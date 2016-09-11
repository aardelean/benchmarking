package under.spring;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class Endpoint {
    private static final  String responseString = "plane damn text default result!";

    @RequestMapping(path = "/default", method = RequestMethod.GET, produces = "text/plain")
    public String getDefault(){
        return responseString;
    }
}
