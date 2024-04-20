package com.wkp.controller.student;

import com.alibaba.fastjson2.JSON;
import com.wkp.controller.BaseServlet;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/GetLessonsServlet")
public class GetLessonsServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取请求的课程ID
        String requestString = (String) req.getAttribute("requestString");
        String courseID = (String) JSON.parseObject(requestString).get("courseID");
        //2. 查询
        String lessons = null;
        String querySql = "SELECT LESSONS FROM COURSES WHERE COURSEID = " + courseID + ";";
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(querySql);
            while(rs.next()){
                lessons = rs.getString("lessons");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //3. 响应
        resp.getWriter().write(lessons);
    }
}
