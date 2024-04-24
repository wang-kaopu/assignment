package com.wkp.controller.teacher;

import com.wkp.controller.BaseServlet;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 设置编码
        req.setCharacterEncoding("UTF-8");
        //2. 获取Part
        Part video = req.getPart("lessonVideo");
        String courseID = String.valueOf(req.getSession().getAttribute("courseID"));
        String lessonID = String.valueOf(req.getSession().getAttribute("lessonID"));
        //3. 获取路径
        String path = req.getServletContext().getRealPath("/");
//        System.out.println("UploadServlet:" + path);
        String name = video.getSubmittedFileName();
        //4. 新建文件夹
        File file = new File(path + "/videos/" + courseID + "/" + lessonID);
        file.mkdirs();
        //5. 上传
        video.write(path + "/videos/" + courseID + "/" + lessonID + "/" + name);
        //6. 更新数据库表
        String updateSql = "UPDATE LESSONS SET LESSONCONTEXT = ? WHERE COURSEID = ? AND LESSONID = ?;";
        JDBCUtils.update(updateSql, "http://localhost:8080/week5/videos/" + courseID + "/" + lessonID + "/" + name,courseID,lessonID);
    }
}