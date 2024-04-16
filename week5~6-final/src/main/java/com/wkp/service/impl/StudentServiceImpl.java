package com.wkp.service.impl;

import com.wkp.service.StudentService;
import com.wkp.utils.JDBCUtils;
import java.sql.SQLException;

public class StudentServiceImpl implements StudentService {
    @Override
    public boolean checkIdentity(String sql, String... params) {
//        for (String param : params) {
//            System.out.println("参数有："+param);
//        }
        try {
            return (JDBCUtils.Query(sql, params));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
