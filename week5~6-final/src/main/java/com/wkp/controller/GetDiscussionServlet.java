package com.wkp.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wkp.po.Message;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@WebServlet("/GetDiscussionServlet")
public class GetDiscussionServlet extends BaseServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取课程ID
        String requestString = (String)req.getAttribute("requestString");
        JSONObject jsonObject = JSON.parseObject(requestString);
        Integer courseID = (Integer)jsonObject.get("courseID");
        //2. 查询讨论区内容
        String sql = "SELECT * FROM DISCUSSION WHERE COURSEID = ?;";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ArrayList<Message> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(sql, courseID);
            while(rs.next()){
                String message = rs.getString("message");
                int id = rs.getInt("personID");
                String name = rs.getString("personName");
                LocalDateTime time = LocalDateTime.parse(rs.getString("time"), formatter);
                list.add(new Message(message,id,name,time));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //3. 转为JSON字符串
        String jsonString = JSON.toJSONString(list);
        //4. 响应
        resp.getWriter().write(jsonString);
    }
}
