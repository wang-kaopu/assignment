package com.wkp.controller.teacher;

import com.alibaba.fastjson2.JSON;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Course;
import com.wkp.po.User;
import com.wkp.service.TeacherService;
import com.wkp.service.impl.TeacherServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/AddCourseServlet")
public class AddCourseServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1. 获取请求
        String requestString = (String) req.getAttribute("requestString");
        Course course = JSON.parseObject(requestString, Course.class);
        //2. 获取相关信息
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        TeacherService teacherService = new TeacherServiceImpl();
        //3. 更新
        Map<String, Integer> addCourseResult = teacherService.addCourse(course, currentUser);
        HashMap<String, Integer> responseMap = new HashMap<>();
        //4. 响应
        Integer courseExecute = addCourseResult.get("courseExecute");
        responseMap.put("courseExecute", courseExecute);
        String responseString = JSON.toJSONString(responseMap);
        Writer writer = resp.getWriter();
        writer.write(responseString);
    }
}
