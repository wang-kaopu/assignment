package com.wkp.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wkp.po.Course;
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

        String requestString = JsonUtils.getRequestString(req);
        req.setAttribute("requestString", requestString);
        JSONObject requestJsonObject = JSON.parseObject(requestString);
        String methodName = (String) requestJsonObject.get("method");
        req.setAttribute("requestJsonObject", requestJsonObject);
        //System.out.println(methodName);
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
            req.setAttribute("identity", "student");
            req.getRequestDispatcher("LoginServlet").forward(req, resp);
            HttpSession session = req.getSession();
            this.currentUser = (User) session.getAttribute("currentUser");
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void teacherLogin(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("identity", "teacher");
            req.getRequestDispatcher("LoginServlet").forward(req, resp);
            HttpSession session = req.getSession();
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
}
