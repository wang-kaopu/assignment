//package com.wkp.servlet;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//
//@WebServlet("/Servlet")
//public class helloServlet extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String parameter = req.getParameter("userString");
//        System.out.println("Paramter:"+parameter);
//        //设置传值的编码
//        resp.setContentType("text/html;charset=UTF-8");
//        req.setCharacterEncoding("utf-8");
//
//        //数据流获取信息
//        StringBuilder sb = new StringBuilder();
//        BufferedReader reader = req.getReader();
//        char[] buf = new char[1024];
//        int len;
//        while ((len = reader.read(buf)) != -1){
//            sb.append(buf,0,len);
//        }
//
//        //转json
//        String str = sb.toString();
//        //System.out.println(str);
//    }
//}
