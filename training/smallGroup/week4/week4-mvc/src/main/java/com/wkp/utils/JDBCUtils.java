package com.wkp.utils;

import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.*;
public class JDBCUtils {
    private static final String USER;//用户名
    private static final String PASSWORD;//密码
    private static final String URL;//url
    private static final String DRIVER;
    static {//static代码块只在第一次使用到所在类时执行
        try{
            Properties props = new Properties();
            //props.load(new FileReader("web/WEB-INF/classes/database.properties"));//加载配置
            InputStream inputStream = JDBCUtils.class.getClassLoader().getResourceAsStream("database.properties");
            props.load(inputStream);
            USER = props.getProperty("user");//为静态成员变量赋值
            PASSWORD = props.getProperty("password");
            URL = props.getProperty("url");
            DRIVER = props.getProperty("driver");
            System.out.println(USER);
            System.out.println(PASSWORD);
            System.out.println(URL);
            System.out.println(DRIVER);
            Class.forName(DRIVER);//注册驱动
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public static Connection getConn(){//获取连接方法
        try {
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void close(Statement statement, Connection conn){//释放资源方法
        try {
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int update(String sql,Object... parms) {
        //String replaceSql = "replace into students (id,name) values (3,'wangwu');";//编写mysql语句
        //1. 获取连接
        Connection conn = getConn();
        //2. 执行查询
        int rs = 0;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            int i = 1;
            for (Object parm : parms) {
                ps.setObject(i,parm);
                i++;
            }
            rs = ps.executeUpdate();
            return rs;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(ps, conn);
        }
    }
    public static int delete(String sql,Object...parms){
        //String sql = "delete from students where id = ?;"
        Connection conn = getConn();
        int rs = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int i = 1;
            for (Object parm : parms) {
                ps.setObject(i,parm);
                i++;
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static boolean Query(String username, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?;";
        Connection conn = getConn();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setObject(1,username);
        ps.setObject(2,password);
        ResultSet rs = ps.executeQuery();
        boolean ret=false;
        while(rs.next()){
            ret=true;
            System.out.println(rs.getString("username"));
            System.out.println(rs.getString("password"));
        }
        return ret;
    }
}
