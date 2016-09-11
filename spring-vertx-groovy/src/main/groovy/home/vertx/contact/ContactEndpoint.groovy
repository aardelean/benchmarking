package home.vertx.contact

import io.advantageous.qbit.annotation.RequestMapping
import io.advantageous.qbit.annotation.RequestMethod
import io.advantageous.qbit.http.request.HttpBinaryResponse
import io.advantageous.qbit.reactive.Callback
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@RequestMapping("")
@Component
public class ContactEndpoint {

    @Autowired
    ContactRepository repository;

    @RequestMapping(value = "/db", method = RequestMethod.GET, contentType = "application/json")
    void getResult(Callback<HttpBinaryResponse> callback){
        repository.getContactById(callback, 1l);
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET, contentType = "text/plain")
    String defaultResult(){
         "plane damn text default result!";
    }
}
