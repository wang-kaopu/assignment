package com.wkp.controller.student;

import com.wkp.controller.BaseServlet;
import com.wkp.utils.JDBCUtils;
import com.wkp.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Writer writer = resp.getWriter();
        Map<String,String> map = new HashMap<>();
        Map<String,String> params = (Map)req.getAttribute("params");
        String email=params.get("email");
        String password=params.get("password");
        String studentName = params.get("studentName");
        String studentGrade = params.get("studentGrade");
        String insertSql = "INSERT IGNORE INTO STUDENTS (EMAIL, PASSWORD, STUDENTNAME, STUDENTGRADE) VALUE (?,?,?,?);";
        int update=0;
        try {
            String sql = "SELECT * FROM STUDENTS WHERE EMAIL = ?;";
            if(!(JDBCUtils.Query(sql, email)||email.equals("null")||password.equals("null"))){
                update = JDBCUtils.update(insertSql, email,password,studentName,studentGrade);
                System.out.println("update:"+update);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (update > 0) {
            map.put("code", "1");
            String selectSql = "SELECT * FROM STUDENTS WHERE EMAIL = ?;";
            try {
                ResultSet rs = JDBCUtils.QueryAndGetResultSet(selectSql,email);
                if(rs.next()){
                    String personID = rs.getString("personID");
                    map.put("personID",personID);
                }
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            map.put("code","0");
        }
        //System.out.println(JsonUtils.mapToJSONString(map,"code"));
        writer.write(JsonUtils.mapToJSONString(map,"code","personID"));
    }
}
