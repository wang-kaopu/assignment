package com.wkp.controller.student;

import com.alibaba.fastjson2.JSON;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Course;
import com.wkp.po.Student;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/JoinCourseServlet")
public class JoinCourseServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取当前学生ID
        Student student = (Student) req.getSession().getAttribute("currentStudent");
        //2. 获取目标课程
        Course course = JSON.parseObject((String) req.getAttribute("requestString"), Course.class);
        //3. 将学生ID写入课程学生名单
        List<String> list = JSON.parseArray(course.getStudentList(), String.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        boolean update = false;
        if (student != null && list.size() < course.getStudentNumberLimitation() && LocalDateTime.now().isAfter(course.getCourseStartTime()) && LocalDateTime.now().isBefore(course.getCourseEndTime())) {
            //判断人数限制、时间
            list.add(student.getPersonID());
            String updateSql = "UPDATE COURSES SET STUDENTSLIST = ? WHERE COURSEID = ?;";
            String updatedList = JSON.toJSONString(list);
            JDBCUtils.update(updateSql, updatedList, course.getCourseID());
            update = true;
        }
        //4. 响应
        HashMap<String, String> map = new HashMap<>();
        if (update) {
            map.put("code", "1");
            resp.getWriter().write(JSON.toJSONString(map));
        } else {
            map.put("code", "0");
            resp.getWriter().write(JSON.toJSONString(map));
        }
    }
}
