package com.wkp.controller.teacher;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Problem;
import com.wkp.service.impl.TeacherServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/TeacherOpenProblemsServlet")
public class TeacherOpenProblemsServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的课程ID和小节ID
        String requestString = (String) req.getAttribute("requestString");
        JSONObject jsonObject = JSON.parseObject(requestString);
        Object courseID = jsonObject.get("courseID");
        Object lessonID = jsonObject.get("lessonID");
        //2. 查询问题
        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        String querySql = "SELECT * FROM PROBLEMS WHERE COURSEID = ? AND LESSONID = ?;";
        List<Problem> problems = teacherService.queryProblems(querySql, courseID, lessonID);
        String jsonString = JSON.toJSONString(problems);
        //3. 响应
        System.out.println(jsonString);
        resp.getWriter().write(jsonString);
    }
}
