package com.wkp.controller;

import com.wkp.utils.JDBCUtils;
import com.wkp.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends BaseServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Writer writer = resp.getWriter();
        Map<String,String> map = new HashMap<>();
        Map<String,String> params = (Map)req.getAttribute("params");
        String insertSql = "REPLACE INTO USERS (USERNAME, PASSWORD) VALUE (?,?);";
        int update = JDBCUtils.update(insertSql, params.get("username"),params.get("password"));
        if(update>0){
           map.put("code","1");
        }else{
            map.put("code","0");
        }
        //System.out.println(JsonUtils.mapToJSONString(map,"code"));
        writer.write(JsonUtils.mapToJSONString(map,"code"));
    }
}
