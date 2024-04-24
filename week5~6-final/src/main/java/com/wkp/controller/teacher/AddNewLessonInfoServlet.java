package com.wkp.controller.teacher;

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
import java.util.HashMap;
import java.util.Random;

@WebServlet("/AddNewLessonInfoServlet")
public class AddNewLessonInfoServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1。获取请求
        JSONObject jsonObject = JSON.parseObject((String) req.getAttribute("requestString"));
        Object courseID = jsonObject.get("courseID");
        Object chapterName = jsonObject.get("chapterName");
        Object lessonName = jsonObject.get("lessonName");

        //2. 查询
        String queryCourseName = "SELECT COURSENAME FROM COURSES WHERE COURSEID = ?;";
        ResultSet rs = null;
        String courseName = null;
        try {
            rs = JDBCUtils.QueryAndGetResultSet(queryCourseName, courseID);
            while (rs.next()) {
                courseName = rs.getString("courseName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //3. 生成小节ID
        int lessonID = new Random().nextInt(9000) + 1000;
        //3. 更新
        String insertSql = "INSERT IGNORE LESSONS (COURSEID, COURSENAME, LESSONID, CHAPTERNAME, LESSONNAME) VALUES (?,?,?,?,?);";
        int update = JDBCUtils.update(insertSql, courseID, courseName, lessonID, chapterName, lessonName);
        //4. 响应
        HashMap<String, Integer> map = new HashMap<>();
        map.put("update", update);
        map.put("lessonID",lessonID);
        req.getSession().setAttribute("lessonID",lessonID);
        req.getSession().setAttribute("courseID",courseID);
        resp.getWriter().write(JSON.toJSONString(map));
    }
}