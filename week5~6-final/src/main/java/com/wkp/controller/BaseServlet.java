package com.wkp.controller;

import com.wkp.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        Map<String, String> map = JsonUtils.getJSONParams(req);
        String methodName = map.get("method");
        req.setAttribute("params",map);
        System.out.println(methodName);
        Class<? extends BaseServlet> actionClass = this.getClass();
        System.out.println(actionClass.getName());
        Method method = null;
        try {
            method = actionClass.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void studentLogin(HttpServletRequest req,HttpServletResponse resp){
        try {
            req.getRequestDispatcher("StudentLoginServlet").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void teacherLogin(HttpServletRequest req,HttpServletResponse resp){
        try {
            req.getRequestDispatcher("TeacherLoginServlet").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(HttpServletRequest req,HttpServletResponse resp){
        try {
            req.getRequestDispatcher("RegisterServlet").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
