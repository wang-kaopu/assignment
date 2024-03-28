package com.wkp.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import com.wkp.utils.getJSONParams;

@WebServlet("/registerServlet")
public class registerServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> map = getJSONParams.getJSONParams(req);
        req.setAttribute("params", map);
        doPost(req, resp);
    }
}
