package home.vertx.qbit.jooq;

import io.advantageous.qbit.annotation.PathVariable;
import io.advantageous.qbit.annotation.RequestMapping;
import io.advantageous.qbit.annotation.RequestMethod;
import io.advantageous.qbit.http.request.HttpBinaryResponse;
import io.advantageous.qbit.reactive.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 18.06.16.
 */

@RequestMapping("")
@Component
public class Endpoint {

    @Autowired
    private ContactService repository;

    @RequestMapping(value = "/db", method = RequestMethod.GET, contentType = "application/json")
    public ContactDomainDTO getResult(){
        return repository.getOneContact(1l);
    }

    @RequestMapping(value = "/db-default", method = RequestMethod.GET, contentType = "application/json")
    public String getDefaultResult(){
        repository.select(1l);
        return "{\"firstName\":\"Andreas\",\"lastName\":\"Loibl\",\"age\":64,\"birthdate\":\"18.12.2011\"}";
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET, contentType = "text/plain")
    public String defaultResult(){
        return "plane damn text default result!";
    }
}
