package com.wkp.controller;

import com.wkp.service.impl.UserServiceImpl;
import com.wkp.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet("/loginServlet")
public class loginServlet extends BaseServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> params = (Map)request.getAttribute("params");
        Iterator<String> iterator = null;
        iterator = params.keySet().iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            String value = params.get(key);
            System.out.println(key+":"+value);
        }
        System.out.println(params.get("username"));
        System.out.println(params.get("password"));
        UserServiceImpl userService = new UserServiceImpl();
        boolean checked = userService.checkIdentity(params.get("username"), params.get("password"));
        Writer writer = response.getWriter();
        Map<String, String> map = new HashMap<>();
        if(checked){
            map.put("code","1");
            System.out.println("登录成功");
        }else{
            map.put("code","0");
            System.out.println(map);
            System.out.println("登录失败");
        }
        //System.out.println(JsonUtils.mapToJSONString(map,"code"));
        writer.write(JsonUtils.mapToJSONString(map,"code"));
    }
}
