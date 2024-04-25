package com.wkp.controller.student;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Answer;
import com.wkp.po.Problem;
import com.wkp.po.Student;
import com.wkp.po.User;
import com.wkp.service.impl.StudentServiceImpl;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/CommitProblemsServlet")
public class CommitProblemsServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取请求校对的题目
        String requestString = (String) req.getAttribute("requestString");
        JSONObject jsonObject = JSON.parseObject(requestString);
        List<Problem> problems = JSON.parseArray((String) jsonObject.get("problems"), Problem.class);
        Problem first = problems.get(0);
        int courseID = first.getCourseID();
        int lessonID = first.getLessonID();
        //检查是否已经有作答记录
        boolean toRecord = true;
        String queryAnswerList = "SELECT * FROM PROBLEMS WHERE COURSEID = ? AND LESSONID = ?;";
        try {
            ResultSet rs = JDBCUtils.QueryAndGetResultSet(queryAnswerList, courseID, lessonID);
            while (rs.next()) {
                String answerList = rs.getString("answerList");
                if (answerList == null) {
                    answerList = new String();
                }
                List<Answer> answers = JSON.parseArray(answerList, Answer.class);
                if (answers == null) {
                    answers = new ArrayList<>();
                }
                for (Answer answer : answers) {
                    if (answer == null || answer.getPersonID() == Integer.valueOf((String) ((Student) req.getSession().getAttribute("currentStudent")).getPersonID())) {
                        toRecord = false;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (toRecord) {
            //2. 查询
            String sql = "SELECT * FROM PROBLEMS WHERE COURSEID = ? AND LESSONID = ?;";
            StudentServiceImpl studentService = new StudentServiceImpl();
            List<Problem> list = studentService.queryCorrectAnswers(sql, courseID, lessonID);
            //3. 评分并记录
            int score = 0;
            int totalScore = 0;
            for (int i = 0; i < problems.size(); i++) {
                //判断是否正确
                int singleScore = 0;
                //类型为选择题并且选错了，则不加分
                if (problems.get(i).getType() == 1) {
                    Integer inputAnswer = Integer.valueOf(problems.get(i).getAnswer());
                    Integer correctAnswer = Integer.valueOf(list.get(i).getCorrectAnswer());
                    if (inputAnswer != correctAnswer) {
                        score += 0;
                    } else {
                        score += 1;
                        singleScore += 1;
                    }
                } else {
                    score += 1;
                    singleScore += 1;
                }
                totalScore += 1;
                //记录答题情况
                Problem currentProblem = problems.get(i);
                String context = currentProblem.getContext();
                String querySql = "SELECT ANSWERLIST FROM PROBLEMS WHERE COURSEID = ? AND LESSONID = ? AND CONTEXT = ?;";
                //更新该problem记录的answerList
                String answerList = null;
                try {
                    ResultSet rs = JDBCUtils.QueryAndGetResultSet(querySql, courseID, lessonID, context);
                    while (rs.next()) {
                        answerList = rs.getString("answerList");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                List<Answer> answers = JSON.parseArray(answerList, Answer.class);
                if (answers == null) {
                    answers = new ArrayList<>();
                }
                answers.add(new Answer(currentProblem.getAnswer(), Integer.parseInt(((User) req.getSession().getAttribute("currentUser")).getPersonID()), singleScore));
                String newAnswers = JSON.toJSONString(answers);
                String updateSql = "UPDATE PROBLEMS SET ANSWERLIST = ? WHERE COURSEID = ? AND LESSONID = ? AND CONTEXT = ?;";
                JDBCUtils.update(updateSql, newAnswers, courseID, lessonID, context);
            }
            //计算正确率，转换为百分比
            BigDecimal correctRate = BigDecimal.valueOf(score).divide(BigDecimal.valueOf(totalScore));
            correctRate.setScale(2, BigDecimal.ROUND_HALF_UP);
            DecimalFormat decimalFormat = new DecimalFormat("0.00%");
            String correctRateString = decimalFormat.format(correctRate);
            //5. 响应
            HashMap<Object, Object> map = new HashMap<>();
            map.put("list", list);
            map.put("score", score);
            map.put("correctRate", correctRateString);
            resp.getWriter().write(JSON.toJSONString(map));
        } else {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("list", null);
            map.put("score", 0);
            resp.getWriter().write(JSON.toJSONString(map));
        }
    }
}