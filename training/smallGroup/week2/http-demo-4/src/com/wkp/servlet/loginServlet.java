package com.wkp.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username+":"+password);
        Properties properties = new Properties();
        properties.load(new FileReader("src/com/wkp/resources/data.properties"));
        if(properties.getProperty(username).equals(password)){
            System.out.println("登录成功");
        }
    }
}
