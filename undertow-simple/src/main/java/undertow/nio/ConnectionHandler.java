package undertow.nio;

import undertow.HandlerPath;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 9/7/2015.
 */
@Component
@HandlerPath(path="/json")
public class ConnectionHandler implements HttpHandler {
    private static final  String responseString = "plane damn text default result!";

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
       exchange.getResponseSender().send(responseString);
    }
}
