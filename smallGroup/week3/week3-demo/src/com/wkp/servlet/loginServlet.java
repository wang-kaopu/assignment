package com.wkp.servlet;

import com.wkp.utils.getJSONParams;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> map = getJSONParams.getJSONParams(req);
        String username = map.get("username");
        String password = map.get("password");
        System.out.println(username+":"+password);
        if(username.equals("zhangsan")&&password.equals("123")){
            System.out.println("登陆成功");
            resp.getWriter().write("1");
       }
    }
}
