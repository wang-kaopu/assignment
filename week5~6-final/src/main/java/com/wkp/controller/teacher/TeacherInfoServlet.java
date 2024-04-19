package com.wkp.controller.teacher;

import com.wkp.controller.BaseServlet;
import com.wkp.po.Teacher;
import com.wkp.service.impl.TeacherServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/TeacherInfoServlet")
public class TeacherInfoServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("刀客这里");
        Teacher currentTeacher = (Teacher) req.getSession().getAttribute("currentTeacher");
        //System.out.println(currentTeacher);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        String teacherInfo = teacherService.getTeacherInfo(currentTeacher);
        //System.out.println(teacherInfo);
        PrintWriter writer = resp.getWriter();
        writer.write(teacherInfo);
    }
}
