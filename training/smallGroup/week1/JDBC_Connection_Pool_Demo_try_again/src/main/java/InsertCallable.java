import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.Callable;

public class InsertCallable implements Callable<Integer> {
    private final ConnectionPool cp;
    private final String insertSql;
    public InsertCallable(ConnectionPool cp, String insertSql){
        this.cp = cp;
        this.insertSql = insertSql;
    }
    @Override
    public Integer call() throws Exception {
        Connection connection = cp.getConnection();
        PreparedStatement ps = connection.prepareStatement(insertSql);
        cp.releaseConnection(connection);
        return ps.executeUpdate();
    }
}