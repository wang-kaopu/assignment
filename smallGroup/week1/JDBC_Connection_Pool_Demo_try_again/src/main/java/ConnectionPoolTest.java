import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPoolTest {
    public static void main(String[] args) throws SQLException {
        ConnectionPool connectionPool = new ConnectionPool();
        Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement("select  * from students");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            try {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.print(id);
                System.out.println("："+name);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        connectionPool.releaseConnection(connection);
    }
}
