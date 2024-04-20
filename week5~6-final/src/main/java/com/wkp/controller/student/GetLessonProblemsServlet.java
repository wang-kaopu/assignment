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

@WebServlet("/GetLessonProblemsServlet")
public class GetLessonProblemsServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取课程ID和小节ID
        String requestString = (String) req.getAttribute("requestString");
        JSONObject jsonObject = JSON.parseObject(requestString);
        Integer courseID = (Integer) jsonObject.get("courseID");
        Integer lessonID = (Integer) jsonObject.get("lessonID");
        //2. 查询
        String sql = "SELECT * FROM PROBLEMS WHERE COURSEID = ? AND LESSONID = ?;";
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(sql, courseID, lessonID);
            while(rs.next()){
                //根据表设计题目！！！！！！！！！！！！
                //配合开发管理课程
//                !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
