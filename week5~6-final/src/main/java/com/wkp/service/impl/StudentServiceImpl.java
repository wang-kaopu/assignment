package com.wkp.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.filter.SimplePropertyPreFilter;
import com.wkp.po.*;
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

    public String queryProblems(String sql, Object... params) {
        //1. 查询
        String result = null;
        ArrayList<Problem> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(sql, params);
            while (rs.next()) {
                String context = rs.getString("context");
                int courseID = rs.getInt("courseID");
                int lessonID = rs.getInt("lessonID");
                int type = rs.getInt("type");
                list.add(new Problem(context, lessonID, courseID, type));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //2. 转为JSON字符串并返回
        return JSON.toJSONString(list);
    }

    public List queryCorrectAnswers(String sql, Object... params) {
        //1. 查询正确答案
        ArrayList<Problem> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(sql, params);
            while (rs.next()) {
                String context = rs.getString("context");
                int courseID = rs.getInt("courseID");
                int lessonID = rs.getInt("lessonID");
                String correctAnswer = rs.getString("correctAnswer");
                int type = rs.getInt("type");
                list.add(new Problem(context, null, lessonID, courseID, type, correctAnswer));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void addStudyRecord(User user, Integer courseID, Integer lessonID) {
        //1. 查询
        String studyrecord = null;
        String querySql = "SELECT STUDYRECORD FROM LESSONS WHERE COURSEID = ? AND LESSONID = ?;";
        ResultSet rs = null;
        try {
            rs = JDBCUtils.QueryAndGetResultSet(querySql, courseID, lessonID);
            while (rs.next()) {
                studyrecord = rs.getString("studyrecord");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //2. 转换
        List<User> list = new ArrayList<>();
        if (studyrecord != null) {
            list = JSON.parseArray(studyrecord, User.class);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        //3. 更新
        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter(User.class, "personID", "name");
        list.add(user);
        String insertSql = "UPDATE LESSONS SET STUDYRECORD = ? WHERE COURSEID = ? AND LESSONID = ?;";
        JDBCUtils.update(insertSql, JSON.toJSONString(list, simplePropertyPreFilter), courseID, lessonID);
    }

    public List<Course> getStudyRecord(String personID) {
        //2. 查询课程学生列表中含有该学生ID的课程
        ArrayList<Course> courses = new ArrayList<>();
        String queryCoursesSql = "SELECT * FROM COURSES";
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(queryCoursesSql);
            List<String> studentList = new ArrayList<>();
            while (rs.next()) {
                studentList = JSON.parseArray(rs.getString("studentsList"), String.class);
                for (String s : studentList) {
                    if (s.equals(personID)) {
                        courses.add(new Course(rs.getInt("courseID"), rs.getString("courseName")));
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //3. 查询课程小节列表中含有该学生ID学习记录的小节
        String queryLessonSql = "SELECT * FROM LESSONS WHERE COURSEID = ?;";
        for (Course course : courses) {
            try {
                ResultSet rs = JDBCUtils.QueryAndGetResultSet(queryLessonSql, course.getCourseID());
                while (rs.next()) {
                    List<User> studyRecord = JSON.parseArray(rs.getString("studyRecord"), User.class);
                    if (studyRecord == null) {
                        studyRecord = new ArrayList<>();
                    }
                    for (User u : studyRecord) {
                        if (u.getPersonID().equals(personID)) {
                            Lesson lesson = new Lesson(course.getCourseID(), rs.getString("lessonName"), rs.getInt("lessonID"), rs.getString("chapterName"));
                            List<Lesson> lessons = course.getLessons();
                            if (lessons == null) {
                                lessons = new ArrayList<>();
                            }
                            lessons.add(lesson);
                            course.setLessons(lessons);
                            break;
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return courses;
    }

    public List<Problem> getProblemRecord(Integer courseID, Integer lessonID, String personID) {
        //1. 编写sql语句
        String queryProblems = "SELECT * FROM PROBLEMS WHERE COURSEID = ? AND LESSONID = ?;";
        //2. 查询指定小节的题目
        ArrayList<Problem> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(queryProblems, courseID, lessonID);
            while (rs.next()) {
                //3. 检查该题目是否该学生ID作答的记录
                String answerList = rs.getString("answerList");
                List<Answer> answers = JSON.parseArray(answerList, Answer.class);
                if (answers != null) {
                    for (Answer answer : answers) {
                        if (answer.getPersonID() == Integer.valueOf(personID)) {
                            //4. 若有记录，将该题目加入list
                            String context = rs.getString("context");
                            int type = rs.getInt("type");
                            String correctAnswer = rs.getString("correctAnswer");
                            int singleScore = answer.getSingleScore();
                            String answerContext = answer.getAnswer();
                            list.add(new Problem(context,answerContext,lessonID,courseID,type,correctAnswer,singleScore));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //4. 返回
        return list;
    }
}