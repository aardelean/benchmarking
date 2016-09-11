package home.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class StartVerticle extends AbstractVerticle {

    public static void main(String[] args){
        DeploymentOptions options = new DeploymentOptions().setInstances(8);
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(StartVerticle.class.getName(), options);
    }

    @Override
    public void start() throws Exception {
        super.start();
        vertx.createHttpServer().requestHandler(request -> {
            // This handler will be called every time an HTTP request is received at the server
            request.response().end("plane damn text default result!");
        }).listen(8888);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
