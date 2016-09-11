package home.vertx

import home.vertx.contact.ContactEndpoint
import io.advantageous.qbit.server.EndpointServerBuilder
import io.advantageous.qbit.server.ServiceEndpointServer
import io.advantageous.qbit.vertx.http.VertxHttpServerBuilder
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by alex on 16.07.16.
 */

@Configuration
class VertxConfig {
    @Bean
    Vertx vertx(){
        Vertx.vertx()
    }

    @Bean
    HttpServer httpServer(Vertx vertx){
        vertx.createHttpServer()
    }

    @Bean
    io.advantageous.qbit.http.server.HttpServer qbitServer(HttpServer server, Vertx vertx) {
        VertxHttpServerBuilder.vertxHttpServerBuilder()
                .setHttpServer(server)
                .setVertx(vertx)
                .build();
    }

    @Bean
    ServiceEndpointServer serviceEndpointServer(io.advantageous.qbit.http.server.HttpServer qbitServer,
                                                ContactEndpoint endpoint){
        return EndpointServerBuilder.endpointServerBuilder()
                .setHttpServer(qbitServer)
                .setUri("/api/")
                .addServices(endpoint)
                .build();
    }
}
