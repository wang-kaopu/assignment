package com.wkp.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/uploadServlet")
@MultipartConfig()
public class uploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("文件上传……");
        //1. 设置请求编码
        req.setCharacterEncoding("UTF-8");
        //2. 获取普通请求参数
        String type = req.getParameter("type");
        String id = req.getParameter("id");
        System.out.println(type+":"+id);
        //3. 获取文件路径与文件名
        Part part = req.getPart("file1");
        String name = part.getSubmittedFileName();
        System.out.println("上传的文件名："+name);
        String path = req.getServletContext().getRealPath("/");
        System.out.println("文件上传的路径："+path);
        System.out.println(path+"/"+name);
        //4. 上传
        part.write(path+"/"+name);
    }
}
