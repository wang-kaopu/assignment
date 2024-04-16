package com.wkp.service;

import com.wkp.po.Course;
import com.wkp.po.User;

import java.util.Map;

public interface TeacherService {
    public Map<String,Integer> addCourse(Course course,User currentUser);
}
