package com.wkp.service.impl;

import com.alibaba.fastjson2.JSON;
import com.wkp.po.Course;
import com.wkp.po.Problem;
import com.wkp.po.Student;
import com.wkp.service.StudentService;
import com.wkp.utils.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    @Override
    public boolean checkIdentity(String sql, String... params) {
        try {
            return (JDBCUtils.Query(sql, params));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Course> queryCourses(String querySql) {
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
                list.add(new Course(courseID, courseName, courseDescription, courseStartTime, courseEndTime, studentNumberLimitation, String.valueOf(teacherID), teacherName, studentsList));
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

    public String queryLessons(String sql) {
        //1. 查询
        String result = null;
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(sql);
            while (rs.next()) {
                result = rs.getString("lessons");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //2. 返回
        return result;
    }

    public String queryProblems(String sql, Object...params){
        //1. 查询
        String result=null;
        ArrayList<Problem> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(sql, params);
            while (rs.next()){
                String context = rs.getString("context");
                String answer = rs.getString("answer");
                int courseID = rs.getInt("courseID");
                int lessonID = rs.getInt("lessonID");
                list.add(new Problem(context, answer, lessonID, courseID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //2. 转为JSON字符串并返回
        return JSON.toJSONString(list);
    }

    public List queryCorrectAnswers(String sql, Object... params){
        //1. 查询正确答案
        ArrayList<Problem> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(sql, params);
            while(rs.next()){
                String context = rs.getString("context");
                String answer = rs.getString("answer");
                int courseID = rs.getInt("courseID");
                int lessonID = rs.getInt("lessonID");
                String correctAnswer = rs.getString("correctAnswer");
                int type = rs.getInt("type");
                list.add(new Problem(context, answer, lessonID, courseID,type,correctAnswer));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
