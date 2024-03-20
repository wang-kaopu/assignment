import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.Callable;

public class QueryCallable implements Callable<ResultSet> {

    private final ConnectionPool cp;
    private final String querySql;
    public QueryCallable(ConnectionPool cp, String querySql){
        this.cp = cp;
        this.querySql = querySql;
    }

    @Override
    public ResultSet call() throws Exception {
        Connection connection = cp.getConnection();
        PreparedStatement ps = connection.prepareStatement(querySql);
        ResultSet rs = ps.executeQuery();
        cp.releaseConnection(connection);
        return rs;
    }
}