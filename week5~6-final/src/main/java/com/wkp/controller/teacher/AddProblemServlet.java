package com.wkp.controller.teacher;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Problem;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/AddProblemServlet")
public class AddProblemServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取请求内容
        String requestString = (String) req.getAttribute("requestString");
        JSONObject jsonObject = JSON.parseObject(requestString);
        Object courseID = jsonObject.get("courseID");
        Object lessonID = jsonObject.get("lessonID");
        Problem problem = JSON.parseObject(jsonObject.get("newProblem").toString(), Problem.class);
        //2.更新表
        String insertSql
                = "INSERT IGNORE INTO PROBLEMS (COURSEID, LESSONID, CONTEXT, CORRECTANSWER, TYPE) VALUES (?,?,?,?,?);";
        int update = JDBCUtils.update(insertSql, courseID, lessonID, problem.getContext(), problem.getCorrectAnswer(), problem.getType());
        //3. 响应
        HashMap<String, Integer> map = new HashMap<>();
        map.put("update",update);
        resp.getWriter().write((JSON.toJSONString(map)));
    }
}
