package com.wkp.controller.teacher;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.filter.SimplePropertyPreFilter;
import com.wkp.controller.BaseServlet;
import com.wkp.po.Lesson;
import com.wkp.po.StudyRecord;
import com.wkp.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/LearningSituationServlet")
public class LearningSituationServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取课程ID
        String requestString = (String) req.getAttribute("requestString");
        JSONObject jsonObject = JSON.parseObject(requestString);
        Object courseID = jsonObject.get("courseID");
        //2.查询
        ArrayList<StudyRecord> list = new ArrayList<>();
        String querySql = "SELECT * FROM LESSONS WHERE COURSEID = ?;";
        ResultSet rs = null;
        ArrayList<Lesson> lessons = new ArrayList<>();
        try {
            rs = JDBCUtils.QueryAndGetResultSet(querySql, courseID);
            while (rs.next()) {
                List<String> studyRecord = JSON.parseArray(rs.getString("studyRecord"), String.class);
                String lessonName = rs.getString("lessonName");
                lessons.add(new Lesson(lessonName, studyRecord));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //3. 转换
        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter(Lesson.class, "lessonName", "studyRecord");
        String jsonString = JSON.toJSONString(lessons, simplePropertyPreFilter);
        //4. 响应
        resp.getWriter().write(jsonString);
    }
}
