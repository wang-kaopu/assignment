package com.wkp.controller;

import com.alibaba.fastjson2.JSON;
import com.wkp.po.Identity;
import com.wkp.po.Student;
import com.wkp.po.User;
import com.wkp.service.impl.StudentServiceImpl;
import com.wkp.utils.JDBCUtils;
import com.wkp.utils.JsonUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet("/LoginServlet")
public class LoginServlet extends BaseServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sql = null;
        String identity = (String) request.getAttribute("identity");
        if (identity.equals("student")) {
            sql = "SELECT * FROM STUDENTS WHERE PERSONID = ? AND PASSWORD = ?;";
        } else if (identity.equals("teacher")) {
            sql = "SELECT * FROM TEACHERS WHERE PERSONID = ? AND PASSWORD = ?;";
        }
        System.out.println("sql:" + sql);
        String requestString = (String) request.getAttribute("requestString");
        StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
        //System.out.println("requestString:"+requestString);
        Map<String, String> params = JSON.parseObject(requestString, Map.class);
        String personID = params.get("personID");
        //System.out.println(personID);
        String password = params.get("password");
        //System.out.println(password);


        boolean checked = studentServiceImpl.checkIdentity(sql, personID, password);

        Writer writer = response.getWriter();
        Map<String, String> map = new HashMap<>();
        if (checked) {
            map.put("code", "1");
            System.out.println("登录成功");
        } else {
            map.put("code", "0");
            System.out.println(map);
            System.out.println("登录失败");
        }
        HttpSession session = request.getSession();
        String QueryNameSql=null;
        String name = null;
        ResultSet rs=null;
        if (identity.equals("student")) {
            try {
                QueryNameSql = "SELECT * FROM STUDENTS WHERE PERSONID = ?;";
                rs = JDBCUtils.QueryAndGetResultSet(QueryNameSql, personID);
                while(rs.next()){
                    name = rs.getString("studentName");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (identity.equals("teacher")) {
            QueryNameSql="SELECT * FROM TEACHERS WHERE PERSONID = ?;";
            try {
                rs = JDBCUtils.QueryAndGetResultSet(QueryNameSql, personID);
                while(rs.next()){
                    name = rs.getString("teacherName");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        //System.out.println(new User(Identity.valueOf(identity), personID, name));
        session.setAttribute("currentUser", new User(Identity.valueOf(identity), personID, name));
        //System.out.println(JsonUtils.mapToJSONString(map, "code"));
        writer.write(JsonUtils.mapToJSONString(map, "code"));
    }
}
