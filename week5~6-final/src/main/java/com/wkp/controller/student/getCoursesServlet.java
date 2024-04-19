package com.wkp.controller.student;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Course;
import com.wkp.po.Student;
import com.wkp.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/getCoursesServlet")
public class getCoursesServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获得当前学生ID
        Student student = (Student) req.getSession().getAttribute("currentStudent");
        String personID = student.getPersonID();
        //2. 编写sql查询语句
        String querySql =
                "SELECT * FROM COURSES";
        //3. 根据ID分别查询正在学习的课程、未在学习的课程
        StudentServiceImpl studentService = new StudentServiceImpl();
        ArrayList<Course> courses = studentService.queryCourses(querySql);
        ArrayList<Course> learning = new ArrayList<>();
        ArrayList<Course> other = new ArrayList<>();
        for (Course c : courses) {
            //在课程的学生列表里找是否有学生ID，判断该学生是否参加了该课程
            String listString = c.getStudentList();
            List<String> list = JSON.parseArray(listString, String.class);
            boolean isInvolved = false;
            if (list != null) {
                for (String s : list) {
                    if (s != null && s.equals(personID)) {
                        isInvolved = true;
                        break;
                    }
                }
                if (isInvolved) {
                    learning.add(c);
                } else {
                    other.add(c);
                }
            } else {
                other.add(c);
            }
        }
        //4.设置域对象
        req.getSession().setAttribute("learningCourses", learning);
        req.getSession().setAttribute("otherCourses", other);
        //5. 响应
        String learningCoursesString = JSON.toJSONString(learning);
        String otherCoursesString = JSON.toJSONString(other);
        ArrayList<String> list = new ArrayList<>();
        list.add(learningCoursesString);
        list.add(otherCoursesString);
        resp.getWriter().write(JSON.toJSONString(list));
    }
}