package com.wkp.service.impl;

import com.wkp.utils.JDBCUtils;
import com.wkp.service.UserService;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    @Override
    public boolean checkIdentity(String username, String password) {
        try {
            return (JDBCUtils.Query(username,password));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
