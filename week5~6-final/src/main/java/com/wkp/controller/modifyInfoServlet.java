package com.wkp.controller;

import com.alibaba.fastjson2.JSON;
import com.wkp.po.Identity;
import com.wkp.po.Student;
import com.wkp.po.Teacher;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/modifyInfoServlet")
public class modifyInfoServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获得响应字符串
        String requestString = (String) req.getAttribute("requestString");
        //2. 确认身份
        Identity identity = (Identity) req.getSession().getAttribute("identity");
        //3. 新的信息转换为实体类
        if(identity.equals(Identity.teacher)){
            Teacher teacher = JSON.parseObject(requestString, Teacher.class);
//            Teacher teacher = (Teacher)req.getSession().getAttribute("currentTeacher");
            //4.拿到各个参数
            //System.out.println(teacher);
            String name = teacher.getTeacherName();
            String email = teacher.getEmail();
            String teacherQQ = teacher.getTeacherQQ();
            String teacherDescription = teacher.getTeacherDescription();
            //5。编写sql更新语句
            String updateSql = "UPDATE TEACHERS SET TEACHERNAME = ?, EMAIL = ?, TEACHERQQ = ?, TEACHERDESCRIPTION = ? WHERE PERSONID = ?;";
            //6. 更新
            int update = JDBCUtils.update(updateSql, name, email, teacherQQ, teacherDescription,teacher.getPersonID());
            //7. 检查更新结果，并返回响应
            HashMap<String, Integer> map = new HashMap<>();
            map.put("update",update);
            String jsonString = JSON.toJSONString(map);
            resp.getWriter().write(jsonString);
        } else if (identity.equals(Identity.student)) {
            Student student = JSON.parseObject(requestString, Student.class);
            //4. 拿到各个参数
            //还没测试
            String studentName = student.getStudentName();
            String email = student.getEmail();
            String studentDescription = student.getStudentDescription();
            int studentGrade = student.getStudentGrade();
            //5. 编写sql更新语句
            String updateSql = "UPDATE STUDENTS SET STUDENTNAME = ?, EMAIL = ?, STUDENTGRADE = ?, STUDENTDESCRIPTION = ? WHERE PERSONID = ?;";
            //6. 更新
            int update = JDBCUtils.update(updateSql, studentName, email, studentGrade, studentDescription, student.getPersonID());
            //7. 返回更新结果
            HashMap<String,Integer> map = new HashMap<>();
            map.put("update",update);
            resp.getWriter().write(JSON.toJSONString(map));
        }
        }
}
