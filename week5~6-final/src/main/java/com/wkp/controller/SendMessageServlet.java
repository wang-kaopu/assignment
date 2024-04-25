package com.wkp.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wkp.po.Identity;
import com.wkp.po.User;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet("/SendMessageServlet")
public class SendMessageServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取需要发出的信息
        Object context = req.getAttribute("context");
        JSONObject message = new JSONObject();
        message.put("context", context);
        //2. 查询发送人信息
        String name = null;
        User user = (User) req.getSession().getAttribute("currentUser");
        if(user.getIdentity()== Identity.student){
            String queryNameSql = "SELECT * FROM STUDENTS WHERE PERSONID = ?;";
            try {
                ResultSet rs = JDBCUtils.QueryAndGetResultSet(queryNameSql, user.getPersonID());
                while(rs.next()){
                    name = rs.getString("studentName");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            String queryNameSql = "SELECT * FROM TEACHERS WHERE PERSONID = ?;";
            try {
                ResultSet rs = JDBCUtils.QueryAndGetResultSet(queryNameSql, user.getPersonID());
                while (rs.next()){
                    name = rs.getString("teacherName");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        message.put("name", name);
        message.put("time", LocalDateTime.now());
        //3. 发出
//        InetAddress address = InetAddress.getByName("127.0.0.1");
        //1. 创建DatagramSocket对象
        DatagramSocket socket = new DatagramSocket();

        //2. 将数据打包好
        //注意数据将是以字节数组的形式发出，因而需要给打包类Datagram传一个字节数组
//        String str = "So slay......";
//        byte[] bytes = str.getBytes();
        byte[] bytes = JSON.toJSONString(message).getBytes();
        InetAddress address = InetAddress.getByName("127.0.0.1");
        int port = 10086;
        //打包需要传递的参数：字节数组，（字节数组的起始索引），要发送的的长度，目标IP对象，目标端口号
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length,address, port);

        //3. 把打包好的数据发出
        socket.send(packet);

        //4. 释放socket资源（付钱走人）
        socket.close();
    }
}
