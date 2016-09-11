package sync.dao;

import co.paralleluniverse.fibers.Suspendable;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sync.Sync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class Repository {

    @Autowired
    private DataSource ds;


    @Suspendable
    public Object getOne(Long id) {
        try (Connection conn = ds.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("select * from contact where id=" + id);
            return rs.first();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }
}