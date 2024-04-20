package com.wkp.controller.teacher;

import com.alibaba.fastjson2.JSON;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Course;
import com.wkp.po.Teacher;
import com.wkp.po.User;
import com.wkp.service.impl.TeacherServiceImpl;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/ManageCoursesServlet")
public class ManageCoursesServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取教师ID
        User user = (User) req.getSession().getAttribute("currentUser");
        String personID = user.getPersonID();
        //2. 查询该ID下的课程
        String sql = "SELECT * FROM COURSES WHERE TEACHERID = ?;";
        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        ArrayList<Course> courses = teacherService.queryCourses(sql, personID);
        //3. 转为JSON字符串
        String jsonString = null;
        if (courses != null) {
            jsonString = JSON.toJSONString(courses);
        }
        //4. 响应
        resp.getWriter().write(jsonString);
    }
}