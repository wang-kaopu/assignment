package com.wkp.controller.student;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Problem;
import com.wkp.po.User;
import com.wkp.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/GetProblemRecordServlet")
public class GetProblemRecordServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取课程ID、小节ID和个人ID
        String requestString = (String)req.getAttribute("requestString");
        JSONObject jsonObject = JSON.parseObject(requestString);
        Integer courseID = (Integer)jsonObject.get("courseID");
        Integer lessonID = (Integer) jsonObject.get("lessonID");
        String personID = ((User) req.getSession().getAttribute("currentUser")).getPersonID();
        //2. 查询
        StudentServiceImpl studentService = new StudentServiceImpl();
        List<Problem> problems = studentService.getProblemRecord(courseID, lessonID, personID);
        //3. 算分
        int score = 0;
        for (Problem problem : problems) {
            score+=problem.getSingleScore();
        }
        //4. 序列化
        HashMap<Object, Object> map = new HashMap<>();
        map.put("problems",problems);
        map.put("score",score);
        String jsonString = JSON.toJSONString(map);
        //5. 响应
        resp.getWriter().write(jsonString);
    }
}
