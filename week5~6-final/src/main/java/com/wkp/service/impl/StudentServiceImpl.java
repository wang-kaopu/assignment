package com.wkp.service.impl;

import com.alibaba.fastjson2.JSON;
import com.wkp.po.Course;
import com.wkp.po.Student;
import com.wkp.service.StudentService;
import com.wkp.utils.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class StudentServiceImpl implements StudentService {
    @Override
    public boolean checkIdentity(String sql, String... params) {
        try {
            return (JDBCUtils.Query(sql, params));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Course> queryCourses(String querySql){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ArrayList<Course> list = new ArrayList<>();
        try (ResultSet rs = JDBCUtils.QueryAndGetResultSet(querySql)) {
            while (rs.next()) {
                //拿到参数
                String courseName = rs.getString("courseName");
                int courseID = rs.getInt("courseID");
                String courseDescription = rs.getString("courseDescription");
                LocalDateTime courseStartTime = LocalDateTime.parse(rs.getString("courseStartTime"), formatter);
                LocalDateTime courseEndTime = LocalDateTime.parse(rs.getString("courseEndTime"), formatter);
                String teacherName = rs.getString("teacherName");
                int teacherID = rs.getInt("teacherID");
                int studentNumberLimitation = rs.getInt("studentNumberLimitation");
                String studentsList = rs.getString("studentsList");
                //创建对象，加入list
                list.add(new Course(courseID,courseName,courseDescription,courseStartTime,courseEndTime,studentNumberLimitation,String.valueOf(teacherID),teacherName, studentsList));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public String getStudentInfo(String studentID) {
        //1. 编写sql语句
        String querySql = "SELECT * FROM STUDENTS WHERE PERSONID = ?;";
        //2. 查询
        Student student = null;
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(querySql, studentID);
            while (rs.next()) {
                String studentName = rs.getString("studentName");
                String email = rs.getString("email");
                String studentDescription = rs.getString("studentDescription");
                int studentGrade = rs.getInt("studentGrade");
                student = new Student(studentName, studentID, email, studentDescription, studentGrade);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return JSON.toJSONString(student);
    }
}
