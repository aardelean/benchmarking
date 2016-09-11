package comsat.spring;

import co.paralleluniverse.springframework.boot.autoconfigure.web.FiberSpringBootApplication;
import org.springframework.boot.SpringApplication;

/**
 * Created by alex on 25.06.16.
 */


@FiberSpringBootApplication
public class Starter {
    public static void main(String[] args){
        SpringApplication.run(Starter.class);
    }
}
