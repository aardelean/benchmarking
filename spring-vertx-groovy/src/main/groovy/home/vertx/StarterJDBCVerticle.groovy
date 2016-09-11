package home.vertx

import io.advantageous.qbit.server.ServiceEndpointServer
import io.vertx.core.http.HttpServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

import javax.annotation.PostConstruct

/**
 * Created by alex on 16.07.16.
 */
@Configuration
@Import([DatabaseConfig.class, VertxConfig.class])
@ComponentScan(["home.vertx", "home.vertx.contact"])
@PropertySource("classpath:/application.properties")
class StarterJDBCVerticle {

    @Autowired
    ServiceEndpointServer serviceEndpointServer;

    @Autowired
    HttpServer server;

    @Bean
    static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    static void main(String[] args) throws Exception {
        SpringApplication.run StarterJDBCVerticle
    }

    @PostConstruct
    void gameOn(){
        serviceEndpointServer.start();
        server.listen(8888);
    }
}

