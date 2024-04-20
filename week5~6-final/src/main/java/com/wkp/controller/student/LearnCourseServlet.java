package com.wkp.controller.student;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Course;
import com.wkp.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/LearnCourseServlet")
public class LearnCourseServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取课程ID
        String requestString = (String) req.getAttribute("requestString");
        String courseID = (String) JSON.parseObject(requestString).get("courseID");
        //2. 查询对应信息
        String querySql = "SELECT * FROM COURSES WHERE COURSEID = " + courseID + ";";
        StudentServiceImpl studentService = new StudentServiceImpl();
        ArrayList<Course> courses = studentService.queryCourses(querySql);
        //3. 课程信息转JsonString
        String courseInfo = null;
        if (courses != null) {
            for (Course course : courses) {
                courseInfo = JSON.toJSONString(course);
            }
        }
        if (courseInfo != null) {
            resp.getWriter().write(courseInfo);
        }
    }
}