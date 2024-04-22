package com.wkp.controller.student;

import com.alibaba.fastjson2.JSONObject;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Student;
import com.wkp.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/GetProblemsServlet")
public class GetProblemsServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取课程ID和小节ID
        String requestString = (String)req.getAttribute("requestString");
        JSONObject jsonObject = JSONObject.parseObject(requestString);
        Object courseID = jsonObject.get("courseID");
        Object lessonID = jsonObject.get("lessonID");
        //2. 查询
        String querySql = "SELECT * FROM PROBLEMS WHERE COURSEID = ? AND LESSONID = ?;";
        StudentServiceImpl studentService = new StudentServiceImpl();
        String problemsString = studentService.queryProblems(querySql, courseID, lessonID);
        //3. 响应
        resp.getWriter().write(problemsString);
    }
}
