package com.wkp;

import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    private static final String USER;//用户名
    private static final String PASSWORD;//密码
    private static final String URL;//url
    static {//static代码块只在第一次使用到所在类时执行
        try{
            Properties props = new Properties();
            props.load(new FileReader("db.properties"));//加载配置
            USER = props.getProperty("user");//为静态成员变量赋值
            PASSWORD = props.getProperty("password");
            URL = props.getProperty("url");
            Class.forName(props.getProperty("driver"));//注册驱动
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
    public static void close(ResultSet set, Statement statement, Connection conn){//释放资源方法
        try {
            set.close();
            statement.close();
            conn.close();
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
            int deleteRet = ps.executeUpdate();
            return deleteRet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
