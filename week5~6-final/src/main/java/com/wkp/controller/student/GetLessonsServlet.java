package com.wkp.controller.student;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Lesson;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/GetLessonsServlet")
public class GetLessonsServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取请求的课程ID
        String requestString = (String) req.getAttribute("requestString");
        String courseID = (String) JSON.parseObject(requestString).get("courseID");
        //2. 查询
//        String lessons = null;
        String querySql = "SELECT * FROM LESSONS WHERE COURSEID = ?;";
        ArrayList<JSONObject> lessons = new ArrayList<>();
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(querySql,courseID);
            while(rs.next()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("lessonName",rs.getString("lessonName"));
                jsonObject.put("chapterName",rs.getString("chapterName"));
                jsonObject.put("lessonID",rs.getString("lessonID"));
                jsonObject.put("lessonContext",rs.getString("lessonContext"));
                lessons.add(jsonObject);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //3. 响应
        resp.getWriter().write(JSON.toJSONString(lessons));
    }
}
