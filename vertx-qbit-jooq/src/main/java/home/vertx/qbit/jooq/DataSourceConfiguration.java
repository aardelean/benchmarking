package home.vertx.qbit.jooq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    @Value("${spring.datasource.driver}")
    private String driverClass;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;



    @Bean
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
        datasource.setDriverClassName(driverClass);
        datasource.setUrl(url);
        datasource.setPassword(password);
        datasource.setUsername(username);
        datasource.setMaxActive(200);//important
        datasource.setMinIdle(10);
        datasource.setMaxIdle(10);//release time, important
        datasource.setInitialSize(50);//skip the warm up, we have ram
        return datasource;
    }
}
