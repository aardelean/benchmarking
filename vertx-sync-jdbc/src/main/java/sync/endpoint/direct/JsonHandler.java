package sync.endpoint.direct;

import io.vertx.core.eventbus.Message;

@VertxEndpoint(path = Paths.JSON)
public class JsonHandler implements Endpoint{

    private static final  String responseString = "plane damn text default result!";
    @Override
    public void processGet(Message<Object> request) throws Exception {
        request.reply(responseString);
    }
}
