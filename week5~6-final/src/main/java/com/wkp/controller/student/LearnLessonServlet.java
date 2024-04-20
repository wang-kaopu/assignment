package com.wkp.controller.student;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wkp.controller.BaseServlet;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/LearnLessonServlet")
public class LearnLessonServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取课程和小节ID
        String requestString = (String) req.getAttribute("requestString");
        JSONObject jsonObject = JSON.parseObject(requestString);
        Integer courseID = (Integer) jsonObject.get("courseID");
        Integer lessonID = (Integer) jsonObject.get("lessonID");
        //2. 查询
        String lessonContext = null;
        String sql = "SELECT * FROM LESSONS WHERE COURSEID = ? AND LESSONID = ?;";
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(sql, courseID, lessonID);
            while (rs.next()) {
                lessonContext = rs.getString("lessonContext");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //3. 响应
        resp.getWriter().write(lessonContext);
    }
}
