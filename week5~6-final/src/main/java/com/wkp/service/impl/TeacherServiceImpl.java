package com.wkp.service.impl;

import com.alibaba.fastjson2.JSON;
import com.wkp.po.*;
import com.wkp.service.TeacherService;
import com.wkp.utils.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TeacherServiceImpl implements TeacherService {
    private User currentUser;

    @Override
    public Map<String, Integer> addCourse(Course course, User currentUser) {
        this.currentUser = currentUser;
        Random random = new Random();
        course.setCourseID(random.nextInt(9000) + 1000);
        System.out.println("Impl:" + course.getCourseID());
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
        List<Lesson> lessons = course.getLessons();

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
//        int courseExecute = JDBCUtils.update(courseInsertSql, lessons.toString(), courseID, courseName, courseDescription, courseStartTime, courseEndTime, studentNumberLimitation, teacherID, teacherName);
        int courseExecute = JDBCUtils.update(courseInsertSql, JSON.toJSONString(lessons), courseID, courseName, courseDescription, courseStartTime, courseEndTime, studentNumberLimitation, teacherID, teacherName);

        Map<String, Integer> executeMap = new HashMap<>();
        executeMap.put("courseExecute", courseExecute);
        executeMap.put("lessonExecute", lessonExecute);
        return executeMap;
    }

    public String getTeacherInfo(Teacher teacher) {
        return JSON.toJSONString(teacher);
    }

    public ArrayList queryCourses(String sql, String personID) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ArrayList<Course> list = new ArrayList<>();
        try (ResultSet rs = JDBCUtils.QueryAndGetResultSet(sql, personID)) {
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

    public List<Problem> queryProblems(String sql, Object courseID, Object lessonID) {
        ArrayList<Problem> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(sql, courseID, lessonID);
            while (rs.next()) {
                String context = rs.getString("context");
                int type = rs.getInt("type");
                String correctAnswer = rs.getString("correctAnswer");
                int lessonID1 = rs.getInt("lessonID");
                int courseID1 = rs.getInt("courseID");
                String answerList = rs.getString("answerList");
                list.add(new Problem(context, (String) null, lessonID1, courseID1, type, correctAnswer, (ArrayList<Answer>) JSON.parseArray(answerList,Answer.class)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
