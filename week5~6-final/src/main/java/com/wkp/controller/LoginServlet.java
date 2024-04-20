package com.wkp.controller;

import com.alibaba.fastjson2.JSON;
import com.wkp.po.Identity;
import com.wkp.po.Student;
import com.wkp.po.Teacher;
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
import java.util.Map;

@WebServlet("/LoginServlet")
public class LoginServlet extends BaseServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sql = null;
        //1. 确认身份
        Identity identity = (Identity) request.getSession().getAttribute("identity");
        //2. 编写sql查询语句
        if (identity.equals(Identity.student)) {
            sql = "SELECT * FROM STUDENTS WHERE PERSONID = ? AND PASSWORD = ?;";
        } else if (identity.equals(Identity.teacher)) {
            sql = "SELECT * FROM TEACHERS WHERE PERSONID = ? AND PASSWORD = ?;";
        }
        //3. 获得请求字符串并
        String requestString = (String) request.getAttribute("requestString");
        StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
        //4. 转换为Map
        Map<String, String> params = JSON.parseObject(requestString, Map.class);
        //5. 获得ID和密码
        String personID = params.get("personID");
        String password = params.get("password");
        //6. 在数据库里查询
        boolean checked = studentServiceImpl.checkIdentity(sql, personID, password);
        //7. 编写响应结果
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
        //8. 创建实体类，并作为session域对象
        HttpSession session = request.getSession();
        String QueryNameSql;
        ResultSet rs;
        if (identity.equals(Identity.student)) {
            try {
                QueryNameSql = "SELECT * FROM STUDENTS WHERE PERSONID = ?;";
                rs = JDBCUtils.QueryAndGetResultSet(QueryNameSql, personID);
                while(rs.next()){
                    String studentName = rs.getString("studentName");
                    session.setAttribute("currentStudent",new Student(Identity.student,personID,studentName));
                    session.setAttribute("currentUser",new User(Identity.student,personID,studentName));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (identity.equals(Identity.teacher)) {
            QueryNameSql="SELECT * FROM TEACHERS WHERE PERSONID = ?;";
            try {
                rs = JDBCUtils.QueryAndGetResultSet(QueryNameSql, personID);
                while(rs.next()){
                    String teacherName = rs.getString("teacherName");
                    String teacherQQ = rs.getString("teacherQQ");
                    String email = rs.getString("email");
                    String teacherDescription= rs.getString("teacherDescription");
                    session.setAttribute("currentTeacher",new Teacher(Identity.teacher,personID,teacherName,email,teacherQQ,teacherDescription));
                    session.setAttribute("currentUser",new User(Identity.teacher,personID,teacherName));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        //8. 返回响应结果
        writer.write(JsonUtils.mapToJSONString(map, "code"));
    }
}
