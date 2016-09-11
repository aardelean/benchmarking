package home.vertx.qbit.jooq;

import co.paralleluniverse.fibers.Suspendable;
import io.advantageous.qbit.server.EndpointServerBuilder;
import io.advantageous.qbit.server.ServiceEndpointServer;
import io.advantageous.qbit.vertx.http.VertxHttpServerBuilder;
import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.sync.SyncVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.PostConstruct;

@Configuration
@Import({JOOQConfiguration.class, DataSourceConfiguration.class})
@ComponentScan("home.vertx.thymeleaf.contact")
@PropertySource("classpath:/application.properties")
public class StartJOOQVerticle extends SyncVerticle{

    private static final Vertx vertx = Vertx.vertx();

    @Autowired
    private HttpServer server;

    @Autowired
    private Router router;

    @Autowired
    private ServiceEndpointServer serviceEndpointServer;

    public static void main(String[] args) throws Exception {
        vertx.deployVerticle(StartJOOQVerticle.class.getName());
    }

    @Override
    @Suspendable
    public void start() throws Exception {
        SpringApplication.run(StartJOOQVerticle.class);
        super.start();
    }


    @Bean
    public HttpServer server(){
        HttpServer server = vertx.createHttpServer();
        return server;
    }

    @Bean
    public Router router(){
        Router router = Router.router(vertx);
        return router;
    }

    @Bean
    public io.advantageous.qbit.http.server.HttpServer qbitServer(HttpServer server, Router router){
        io.advantageous.qbit.http.server.HttpServer qbitServer = VertxHttpServerBuilder.vertxHttpServerBuilder()
                .setHttpServer(server)
                .setRouter(router)
                .setVertx(vertx)
                .build();
        return qbitServer;
    }

    @Bean
    public Endpoint endpoint(){
        return new Endpoint();
    }

    @Bean
    public ContactService contactService(){
        return new ContactService();
    }

    @Bean
    public ServiceEndpointServer serviceEndpointServer(io.advantageous.qbit.http.server.HttpServer qbitServer,
                                                       Endpoint endpoint){
        return EndpointServerBuilder.endpointServerBuilder()
                .setHttpServer(qbitServer)
                .setUri("/api/")
                .addServices(endpoint)
                .build();
    }

    @Bean
    public StaticHandler staticHandler(){
        return StaticHandler.create().setWebRoot("dist").setCachingEnabled(false);
    }


    @PostConstruct
    public void gameOn(){
        server.requestHandler(router::accept);
        serviceEndpointServer.startServer();
        server.listen(8888);
    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

}