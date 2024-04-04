package com.wkp.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class getJSONParams {
    public static Map<String, String> getJSONParams(HttpServletRequest req) throws ServletException, IOException {
        // 1. 打开输入流读取客户端写入的json字符串
        InputStream in = req.getInputStream();
        // 2. 使用BufferedReader包装流，指定字符串
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String str = null;
        StringBuilder builder = new StringBuilder();
        // 3. 读取客户端传递的json字符串参数，拼接到StringBuilder中
        while ((str = reader.readLine()) != null) {
            builder.append(str);
        }
        String params = builder.toString();
        // 4. 去除json字符串中的{}
        params = params.substring(1, params.length() - 1);
        // 5. 去除json字符串中的 "
        params = params.replace("\"", "");
        Map<String, String> map = new HashMap<>();
        // 6. 拆分字符串，把key=value键值对, 存放到map中
        String[] arr = params.split(",");
        for (String item : arr) {
            String[] code = item.split(":");
            map.put(code[0], code[1]);
        }
        return map;
    }
}
