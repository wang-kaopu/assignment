package com.wkp.controller.student;

import com.wkp.controller.BaseServlet;
import com.wkp.po.Student;
import com.wkp.service.impl.StudentServiceImpl;
import com.wkp.service.impl.TeacherServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/StudentInfoServlet")
public class StudentInfoServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取学生ID
        Student student = (Student)req.getSession().getAttribute("currentStudent");
        String studentID=null;
        if(student!=null){
            studentID=student.getPersonID();
        }
        StudentServiceImpl studentService = new StudentServiceImpl();
        String studentInfo = studentService.getStudentInfo(studentID);
        PrintWriter writer = resp.getWriter();
        writer.write(studentInfo);
    }
}
