import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
    测试类
 */
public class DruidTest {
    public static void main(String[] args){
        //1. 查询测试
        String selectSql = "select * from students";
        try (Connection conn = DruidUtils.getConnection()){
            PreparedStatement ps = conn.prepareStatement(selectSql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.print(id);
                System.out.print(':');
                System.out.println(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //2. 插入测试
        String insertSql = "insert into students (id,name) values (?,?);";
        try(Connection conn = DruidUtils.getConnection()){
            int inserted = JDBCUtils.insert(conn, insertSql, 3, "wangwu");
            System.out.println(inserted);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        //3. 删除测试
        String deleteSql = "delete from students where id = ?;";
        try(Connection conn = DruidUtils.getConnection()){
            int inserted = JDBCUtils.delete(conn, deleteSql, 3);
            System.out.println(inserted);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
