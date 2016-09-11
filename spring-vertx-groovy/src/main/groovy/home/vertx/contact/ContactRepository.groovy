package home.vertx.contact

import io.advantageous.qbit.http.request.HttpBinaryResponse
import io.advantageous.qbit.http.request.HttpResponseBuilder
import io.advantageous.qbit.reactive.Callback
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.sql.SQLConnection
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import java.sql.ResultSet
import java.util.logging.Logger

@Repository
class ContactRepository {
    static Logger logger = LoggerFactory.getLogger ContactRepository
    @Autowired
    private JDBCClient client

    void getContactById(Callback<HttpBinaryResponse> callback, Long id){
        client.getConnection {
            if (it.succeeded()) {

                SQLConnection connection = it.result();

                connection.query("SELECT * FROM contact WHERE id=" + id, {
                    if (it.succeeded()) {
                        ResultSet rs = it.result();
                        callback.resolve(HttpResponseBuilder.httpResponseBuilder()
                                .setBody(rs.getRows().get(0)).setContentType("application/json")
                                .setCode(200)
                                .buildBinaryResponse());
                        connection.close();
                    }else{
                        connection.close();
                        throw new RuntimeException(res2.cause());
                    }
                });
            } else {
                logger.error("error fetching the contact");
            }
        }
    }
}
