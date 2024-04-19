package com.wkp.service;

import com.wkp.po.Student;

public interface StudentService {
    public boolean checkIdentity(String sql, String... params);
}
