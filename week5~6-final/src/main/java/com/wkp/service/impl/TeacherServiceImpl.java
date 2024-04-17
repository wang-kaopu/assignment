package com.wkp.service.impl;

import com.alibaba.fastjson2.JSON;
import com.wkp.po.*;
import com.wkp.service.TeacherService;
import com.wkp.utils.JDBCUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TeacherServiceImpl implements TeacherService {
    private User currentUser;

    @Override
    public Map<String, Integer> addCourse(Course course, User currentUser) {
        this.currentUser = currentUser;
        Random random = new Random();
        course.setCourseID(random.nextInt(9000) + 1000);
        System.out.println("Impl:"+course.getCourseID());
        course.setTeacherID(currentUser.getPersonID());
        course.setTeacherName(currentUser.getName());
        int courseID = course.getCourseID();
        String courseName = course.getCourseName();
        String courseDescription = course.getCourseDescription();
        LocalDateTime courseStartTime = course.getCourseStartTime();
        LocalDateTime courseEndTime = course.getCourseEndTime();
        int studentNumberLimitation = course.getStudentNumberLimitation();
        String teacherID = currentUser.getPersonID();
        String teacherName = currentUser.getName();
        //String lessonsJsonString = JSON.toJSONString(course.getLessons());
        ArrayList<Lesson> lessons = course.getLessons();

        String lessonInsertSql =
                "INSERT IGNORE INTO LESSONS (COURSEID, LESSONID, LESSONNAME, LESSONCONTEXT, CHAPTERNAME, COURSENAME) VALUE (?,?,?,?,?,?);";
        int lessonExecute = 0;
        for (Lesson lesson : lessons) {
            int lessonID = random.nextInt(9000) + 1000;
            lesson.setLessonID(lessonID);
            String chapterName = lesson.getChapterName();
            String lessonName = lesson.getLessonName();
            String lessonContext = lesson.getLessonContext();
            lessonExecute += JDBCUtils.update(lessonInsertSql, courseID, lessonID, lessonName, lessonContext, chapterName, courseName);
        }

        String courseInsertSql =
                "INSERT IGNORE INTO COURSES (LESSONS, COURSEID, COURSENAME, COURSEDESCRIPTION, COURSESTARTTIME,COURSEENDTIME,STUDENTNUMBERLIMITATION,TEACHERID,TEACHERNAME) VALUE (?,?,?,?,?,?,?,?,?);";
        int courseExecute = JDBCUtils.update(courseInsertSql, lessons.toString(), courseID, courseName, courseDescription, courseStartTime, courseEndTime, studentNumberLimitation, teacherID, teacherName);

        Map<String, Integer> executeMap = new HashMap<>();
        executeMap.put("courseExecute",courseExecute);
        executeMap.put("lessonExecute",lessonExecute);
        return executeMap;
    }

    public String getTeacherInfo(Teacher teacher){
        return JSON.toJSONString(teacher);
    }
}
