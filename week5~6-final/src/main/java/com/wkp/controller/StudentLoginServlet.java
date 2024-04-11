package com.wkp.controller;

import com.wkp.po.Student;
import com.wkp.service.impl.StudentServiceImpl;
import com.wkp.utils.JsonUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet("/StudentLoginServlet")
public class StudentLoginServlet extends BaseServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> params = (Map)request.getAttribute("params");
        Iterator<String> iterator = null;
        iterator = params.keySet().iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            String value = params.get(key);
            System.out.println(key+":"+value);
        }
        StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
        String sql = "SELECT * FROM STUDENTS WHERE PERSONID = ? AND PASSWORD = ?";
        String personID = params.get("personID");
        String password = params.get("password");
        boolean checked = studentServiceImpl.checkIdentity(sql,personID,password);
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
        HttpSession session = request.getSession();
        session.setAttribute("currentStudent",new Student(personID));
        System.out.println(JsonUtils.mapToJSONString(map,"code"));
        writer.write(JsonUtils.mapToJSONString(map,"code"));
    }
}
