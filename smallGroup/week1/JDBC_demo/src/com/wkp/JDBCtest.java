package com.wkp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCtest {
    public static void main(String[] args) throws Exception {
        Connection conn = JDBCUtils.getConn();//获取连接
        String replaceSql = "replace into students (id,name) values (3,'wangwu');";//编写mysql语句
        //String selectSql = "select * from students";
        //PreparedStatement statement = conn.prepareStatement(replaceSql);//准备陈述
        //int executeUpdate = statement.executeUpdate();//返回sql操作的结果
        //System.out.println(executeUpdate);
//        PreparedStatement ps = conn.prepareStatement(selectSql);
//        ResultSet set = ps.executeQuery();
//        while(set.next()){
//            int id = set.getInt("id");
//            String name = set.getString("name");
//            System.out.println(id+name);
//        }
        //JDBCUtils.close(set,statement,conn);
        int updateRet = JDBCUtils.update(replaceSql);
        System.out.println(updateRet);
        int deleted = JDBCUtils.delete("delete from students where id = ?;", "3");
        System.out.println(deleted);

    }
}