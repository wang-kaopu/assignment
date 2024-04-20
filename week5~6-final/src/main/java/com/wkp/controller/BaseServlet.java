package com.wkp.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wkp.po.Course;
import com.wkp.po.Identity;
import com.wkp.po.User;
import com.wkp.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
    private User currentUser;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        //1. 获得请求字符串
        String requestString = JsonUtils.getRequestString(req);
        //2. 设置请求域对象：请求字符串
        req.setAttribute("requestString", requestString);
        JSONObject requestJsonObject = JSON.parseObject(requestString);
        String methodName = (String) requestJsonObject.get("method");
        req.setAttribute("requestJsonObject", requestJsonObject);
        //3. 反射找到对应Servlet
        Class<? extends BaseServlet> actionClass = this.getClass();
        Method method;
        try {
            method = actionClass.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void studentLogin(HttpServletRequest req, HttpServletResponse resp) {
        try {
            //1. 请求转发到loginServlet
            HttpSession session = req.getSession();
            session.setAttribute("identity", Identity.student);
            req.getRequestDispatcher("LoginServlet").forward(req, resp);
            //2. 设置session域对象和req域对象
            this.currentUser = (User) session.getAttribute("currentUser");
            session.setAttribute("currentUser",this.currentUser);
            //session.setAttribute("currentStudent",new Student(Identity.student, this.currentUser.getPersonID(), this.currentUser.getName()));
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void teacherLogin(HttpServletRequest req, HttpServletResponse resp) {
        try {
            //1. 请求转发到loginServlet
            HttpSession session = req.getSession();
            session.setAttribute("identity",Identity.teacher);
            req.getRequestDispatcher("LoginServlet").forward(req, resp);
            //2. 设置session域对象和req域对象
            req.setAttribute("identity",Identity.teacher);
            session.setAttribute("currentUser",session.getAttribute("currentUser"));
            this.currentUser = (User) session.getAttribute("currentUser");
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("RegisterServlet").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCourse(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String requestString = (String) req.getAttribute("requestString");
            Course course = JSON.parseObject(requestString, Course.class);
            req.setAttribute("course", course);
            req.getRequestDispatcher("AddCourseServlet").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getTeacherInfo(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("TeacherInfoServlet").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void getStudentInfo(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("StudentInfoServlet").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void modifyTeacherInfo(HttpServletRequest req,HttpServletResponse resp){
        try {
            req.getRequestDispatcher("modifyInfoServlet").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void modifyStudentInfo(HttpServletRequest req,HttpServletResponse resp){
        try {
            req.getRequestDispatcher("modifyInfoServlet").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getCourses(HttpServletRequest req,HttpServletResponse resp){
        try {
            req.getRequestDispatcher("getCoursesServlet").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void joinCourse(HttpServletRequest req,HttpServletResponse resp){
        try {
            req.getRequestDispatcher("JoinCourseServlet").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void learnCourse(HttpServletRequest req,HttpServletResponse resp){
        try {
            req.getRequestDispatcher("LearnCourseServlet").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getLessons(HttpServletRequest req,HttpServletResponse resp){
        try {
            req.getRequestDispatcher("GetLessonsServlet").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getLessonContent(HttpServletRequest req,HttpServletResponse resp){
        try {
            req.getRequestDispatcher("LearnLessonServlet").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}