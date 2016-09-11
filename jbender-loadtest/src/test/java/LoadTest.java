import java.util.concurrent.ExecutionException;
import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.HdrHistogram.Histogram;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;
import com.pinterest.jbender.JBender;
import com.pinterest.jbender.events.TimingEvent;
import com.pinterest.jbender.events.recording.HdrHistogramRecorder;
import com.pinterest.jbender.events.recording.LoggingRecorder;
import com.pinterest.jbender.executors.http.FiberApacheHttpClientRequestExecutor;

import static com.pinterest.jbender.events.recording.Recorder.record;

public class LoadTest {

    private static final Logger LOG = LoggerFactory.getLogger(LoadTest.class);

    private static int TOTAL_REQUESTS = 1_000_000;
    private static int DB_REQUESTS = 100_000;
    private static String CONNECTION_URL = "http://localhost:8888/api/default";
    private static String DB_URL = "http://localhost:8888/api/db";
    private int CONCURRENT_THREADS = 100;


    @Test
    public void testConnections() throws SuspendExecution, InterruptedException, ExecutionException, IOException {
        durationLoadTest(TOTAL_REQUESTS, CONNECTION_URL);
    }

    @Test
    public void testDBConnections() throws SuspendExecution, InterruptedException, ExecutionException, IOException {
        durationLoadTest(DB_REQUESTS, DB_URL);
    }

    private float durationLoadTest(int requestsNo, String target) throws SuspendExecution, InterruptedException, ExecutionException,  IOException {
        final Histogram histogram = new Histogram(3600000000L, 3);
        final Channel<TimingEvent<CloseableHttpResponse>> eventCh = Channels.newChannel(1000);
        record(eventCh, new HdrHistogramRecorder(histogram, 1000000), new LoggingRecorder(LOG));
        //warm up
        load(10_000, target, eventCh);

        Long start = System.nanoTime();
        load(requestsNo, target, eventCh);

        Long end = System.nanoTime();
        histogram.outputPercentileDistribution(System.out, 1000.0);
        float duration = (end - start) / (float) 1_000_000;
        float thgrouput = (requestsNo / duration) * 1000;
        LOG.info("thgrouput per second is: {} with total time to execute: {} in : {} against : {}",thgrouput, requestsNo, duration, target);
        return duration;
    }

    private void load(int requestsNo, String target, Channel<TimingEvent<CloseableHttpResponse>> eventCh) throws IOException, ExecutionException, InterruptedException {
        try (final FiberApacheHttpClientRequestExecutor requestExecutor =
                     new FiberApacheHttpClientRequestExecutor<>((res) -> {
                         if (res == null) {
                             throw new AssertionError("Response is null");
                         }
                         final int status = res.getStatusLine().getStatusCode();
                         if (status != 200) {
                             throw new AssertionError("Status is " + status);
                         }
                     }, 1000000)) {

            final Channel<HttpGet> requestCh = Channels.newChannel(1000);


            new Fiber<Void>("req-gen", () -> {
                for (int i = 0; i < requestsNo; ++i) {
                    requestCh.send(new HttpGet(target));
                }
                requestCh.close();
            }).start();

            new Fiber<Void>("jbender", () -> {
                JBender.loadTestConcurrency(CONCURRENT_THREADS, 0, requestCh, requestExecutor, eventCh);
            }).start().join();
        }
    }

}