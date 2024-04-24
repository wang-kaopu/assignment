package com.wkp.controller.teacher;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Answer;
import com.wkp.po.Problem;
import com.wkp.service.impl.TeacherServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/TeacherOpenProblemsServlet")
public class TeacherOpenProblemsServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的课程ID和小节ID
        String requestString = (String) req.getAttribute("requestString");
        JSONObject jsonObject = JSON.parseObject(requestString);
        Object courseID = jsonObject.get("courseID");
        Object lessonID = jsonObject.get("lessonID");
        //2. 查询问题
        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        String querySql = "SELECT * FROM PROBLEMS WHERE COURSEID = ? AND LESSONID = ?;";
        List<Problem> problems = teacherService.queryProblems(querySql, courseID, lessonID);
        //3. 计算正确率
        int score = 0;
        int totalScore = 0;
        for (Problem problem : problems) {
            int total = 0;
            int ans = 0;
            String correctAnswer = problem.getCorrectAnswer();
            if (problem.getAnswerList() != null) {
                for (Answer answer : problem.getAnswerList()) {
                    total += 1;
                    if (answer.getAnswer().equals(correctAnswer)) {
                        ans += 1;
                    }
                }
                BigDecimal correctRate = BigDecimal.valueOf(ans).divide(BigDecimal.valueOf(total));
                DecimalFormat decimalFormat = new DecimalFormat("0.00%");
                String formatted = decimalFormat.format(correctRate);
                problem.setCorrectRate(formatted);
            } else {
                problem.setCorrectRate("0.00%");
            }
        }
        String jsonString = JSON.toJSONString(problems);
        //4. 响应
        resp.getWriter().write(jsonString);
    }
}
