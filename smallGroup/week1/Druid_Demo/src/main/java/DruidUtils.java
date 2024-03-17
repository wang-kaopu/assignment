import com.alibaba.druid.pool.DruidDataSource;
import java.sql.Connection;
/*
    Druid工具类
 */

public class DruidUtils {
    private static final DruidDataSource DRS;
    static {
        DRS = DruidConfig.getDrs();
    }
    public static Connection getConnection(){
        try {
            return DRS.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
