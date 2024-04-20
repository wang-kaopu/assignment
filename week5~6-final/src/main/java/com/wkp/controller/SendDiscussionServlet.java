package com.wkp.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wkp.po.User;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

@WebServlet("/SendDiscussionServlet")
public class SendDiscussionServlet extends BaseServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取消息内容以及当前用户ID
        String requestString = (String)req.getAttribute("requestString");
        JSONObject jsonObject = JSON.parseObject(requestString);
        String message = (String)jsonObject.get("message");
        Integer courseID = (Integer) jsonObject.get("courseID");
        User user = (User) req.getSession().getAttribute("currentUser");
        //2. 写入数据库
        String insertSql = "INSERT IGNORE INTO Discussion (MESSAGE, PERSONID, PERSONNAME, COURSEID, TIME) VALUES (?,?,?,?,?);";
        int update = JDBCUtils.update(insertSql, message, user.getPersonID(), user.getName(), courseID, LocalDateTime.now());
//        System.out.println(user.getName());
        //3. 生成更新结果
        HashMap<String, Integer> map = new HashMap<>();
        map.put("update",update);
        String jsonString = JSON.toJSONString(map);
        //4. 响应
        resp.getWriter().write(jsonString);
    }
}
