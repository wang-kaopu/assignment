import java.sql.Connection;
import java.sql.PreparedStatement;
/*
    JDBC工具类
 */
public class JDBCUtils {
    private static int update(Connection conn, String sql, Object[] parms) {
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int i = 1;
            for (Object parm : parms) {
                ps.setObject(i,parm);
                i++;
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static int insert(Connection conn,String insertSql,Object... parms){
        return update(conn, insertSql, parms);
    }

    public static int delete(Connection conn,String deleteSql, Object... parms){
        return update(conn, deleteSql, parms);
    }
}
