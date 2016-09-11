package sync.endpoint.direct;

import co.paralleluniverse.fibers.Suspendable;
import io.vertx.core.eventbus.Message;
import org.springframework.beans.factory.annotation.Autowired;
import sync.dao.Repository;

/**
 * Created by alex on 10/10/2015.
 */
@VertxEndpoint(path = Paths.MYSQL, blocking = true)
public class MysqlHandler implements Endpoint {

    @Autowired
    private Repository repository;

    @Override
    @Suspendable
    public void processGet(Message<Object> request) throws Exception{
        request.reply(repository.getOne(1l).toString());
    }
}
