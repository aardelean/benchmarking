package home.vertx

import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn

/**
 * Created by alex on 16.07.16.
 */
@Configuration
class DatabaseConfig {

    @Value("${spring.datasource.driver}")
    String driverClass;

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;


    @Bean
    @DependsOn(value = "vertx")
    public JDBCClient jdbcClient(Vertx vertx){
        JDBCClient.createShared(vertx, new JsonObject()
                .put("url", url)
                .put("driver_class", driverClass)
                .put("user", username)
                .put("password", password)
                .put("max_pool_size", 200));
    }

}

