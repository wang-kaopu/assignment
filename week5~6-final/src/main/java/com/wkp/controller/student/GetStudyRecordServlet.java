package com.wkp.controller.student;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.filter.SimplePropertyPreFilter;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Course;
import com.wkp.po.Lesson;
import com.wkp.po.User;
import com.wkp.service.impl.StudentServiceImpl;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/GetStudyRecordServlet")
public class GetStudyRecordServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1。 获取当前学生ID
        User user = (User) req.getSession().getAttribute("currentUser");
        String personID = user.getPersonID();
        StudentServiceImpl studentService = new StudentServiceImpl();
        List<Course> courses = studentService.getStudyRecord(personID);
        //4. 序列化
        String jsonString = JSON.toJSONString(courses);
        resp.getWriter().write(jsonString);
    }
}
