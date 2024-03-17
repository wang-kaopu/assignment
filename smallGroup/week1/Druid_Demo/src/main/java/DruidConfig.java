import com.alibaba.druid.pool.DruidDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/*
    Druid的配置类
 */

public class DruidConfig {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    public static final DruidDataSource DRS;
    static {
        //1. 创建DruidDataSource数据源
        DRS = new DruidDataSource();
        //2.设置数据库连接信息
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/druidConfig.properties"));
            URL = properties.getProperty("url");
            USER = properties.getProperty("user");
            PASSWORD = properties.getProperty("password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DRS.setUrl(URL);
        DRS.setUsername(USER);
        DRS.setPassword(PASSWORD);;
        //3. 配置连接池参数
        DRS.setInitialSize(5);//初始化连接数
        DRS.setMaxActive(20);//最大连接数
        DRS.setMinIdle(5);//最小空闲连接数
    }
    public static DruidDataSource getDrs(){
        return DRS;
    }
}
