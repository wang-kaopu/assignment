package com.wkp.controller.student;

import com.alibaba.fastjson2.JSON;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Answer;
import com.wkp.po.Problem;
import com.wkp.po.Student;
import com.wkp.po.User;
import com.wkp.service.StudentService;
import com.wkp.service.impl.StudentServiceImpl;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/CommitProblemsServlet")
public class CommitProblemsServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取请求校对的题目
        String requestString = (String) req.getAttribute("requestString");
        List<Problem> problems = JSON.parseArray(requestString, Problem.class);
        Problem first = problems.get(0);
        int courseID = first.getCourseID();
        int lessonID = first.getLessonID();
        //2. 查询
        String sql = "SELECT * FROM PROBLEMS WHERE COURSEID = ? AND LESSONID = ?;";
        StudentServiceImpl studentService = new StudentServiceImpl();
        List<Problem> list = studentService.queryCorrectAnswers(sql, courseID, lessonID);
        //3. 评分并记录
        int score = 0;
        for (int i = 0; i < problems.size(); i++) {
            //判断是否正确
            if (problems.get(i).getType() == 1 && (problems.get(i).getAnswer().equals(list.get(i).getAnswer()))) {
                score += 0;
            } else {
                score += 1;
            }
            //记录答题情况
            Problem currentProblem = problems.get(i);
            String context = currentProblem.getContext();
            String querySql = "SELECT ANSWERLIST FROM PROBLEMS WHERE COURSEID = ?, LESSONID = ? AND CONTEXT = ?;";
            //更新该problem记录的answerList
            String answerList = null;
            try {
                ResultSet rs = JDBCUtils.QueryAndGetResultSet(querySql, courseID, lessonID, context);
                while (rs.next()) {
                    answerList=rs.getString("answerList");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            List<Answer> answers = JSON.parseArray(answerList, Answer.class);
            answers.add(new Answer(currentProblem.getAnswer(), Integer.parseInt(((User)req.getSession().getAttribute("currentUser")).getPersonID())));
            String newAnswers = JSON.toJSONString(answers);
            String updateSql = "UPDATE PROBLEMS SET ANSWERLIST = ? WHERE COURSEID = ?,LESSONID = ? AND CONTEXT = ?;";
            JDBCUtils.update(updateSql,newAnswers,courseID,lessonID,context);
        }
        //5. 响应
        HashMap<Object, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("score", score);
        resp.getWriter().write(JSON.toJSONString(map));
    }
}
