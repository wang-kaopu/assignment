import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceConfig {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    private static final String DRIVER;
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src\\db.properties"));
            URL = properties.getProperty("url");
            USER = properties.getProperty("user");
            PASSWORD = properties.getProperty("password");
            DRIVER = properties.getProperty("driver");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
