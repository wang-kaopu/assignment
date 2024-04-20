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
        String requestString = (String) req.getAttribute("requestString");
        Course course = JSON.parseObject(requestString, Course.class);
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        TeacherService teacherService = new TeacherServiceImpl();
        Map<String, Integer> addCourseResult = teacherService.addCourse(course, currentUser);
        HashMap<String, Integer> responseMap = new HashMap<>();
        Integer courseExecute = addCourseResult.get("courseExecute");
        Integer lessonExecute = addCourseResult.get("lessonExecute");
        if (courseExecute > 0 && lessonExecute > 0) {
            System.out.println("添加成功");
        } else {
            System.out.println("添加失败");
        }
        responseMap.put("courseExecute", courseExecute);
        responseMap.put("lessonExecute", lessonExecute);
        String responseString = JSON.toJSONString(responseMap);
        Writer writer = resp.getWriter();
        writer.write(responseString);
    }

}
