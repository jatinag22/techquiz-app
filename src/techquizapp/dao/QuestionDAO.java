/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package techquizapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import techquizapp.dbutil.DBConnection;
import techquizapp.pojo.ExamPojo;
import techquizapp.pojo.QuestionPojo;
import techquizapp.pojo.QuestionStore;

/**
 *
 * @author agraw
 */
public class QuestionDAO {
    public static boolean addQuestions(QuestionStore qstore) throws SQLException {
        ArrayList<QuestionPojo> questionList = qstore.getAllQuestions();
        int rem = questionList.size();
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("insert into questions values(?,?,?,?,?,?,?,?,?)");
        for(QuestionPojo qp : questionList) {
            ps.setString(1, qp.getExamId());
            ps.setInt(2, qp.getQno());
            ps.setString(3, qp.getQuestion());
            ps.setString(4, qp.getAnswer1());
            ps.setString(5, qp.getAnswer2());
            ps.setString(6, qp.getAnswer3());
            ps.setString(7, qp.getAnswer4());
            ps.setString(8, qp.getCorrectAnswer());
            ps.setString(9, qp.getLanguage());
            int res = ps.executeUpdate();
            rem-=res;
        }
        return rem==0;
    }
    
    public static QuestionStore getQuestions(String examId) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("select * from questions where examid=?");
        ps.setString(1, examId);
        ResultSet rs = ps.executeQuery();
        QuestionStore qstore = new QuestionStore();
        while(rs.next()) {
            int qno = rs.getInt(2);
            String question = rs.getString(3);
            String answer1 = rs.getString(4);
            String answer2 = rs.getString(5);
            String answer3 = rs.getString(6);
            String answer4 = rs.getString(7);
            String correctAnswer = rs.getString(8);
            String language = rs.getString(9);
            QuestionPojo qp = new QuestionPojo(examId, qno, language, answer1, answer2, answer3, answer4, correctAnswer, question);
            qstore.addQuestion(qp);
        }
        return qstore;
    }
    
    public static boolean updateQuestions(QuestionStore qstore) throws SQLException {
        ArrayList<QuestionPojo> questionList = qstore.getAllQuestions();
        int rem = questionList.size();
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("update questions set question=?, answer1=?, answer2=?, answer3=?, answer4=?, correct_answer=? where qno=? and examid=?");
        for(QuestionPojo qp : questionList) {
            ps.setString(8, qp.getExamId());
            ps.setInt(7, qp.getQno());
            ps.setString(1, qp.getQuestion());
            ps.setString(2, qp.getAnswer1());
            ps.setString(3, qp.getAnswer2());
            ps.setString(4, qp.getAnswer3());
            ps.setString(5, qp.getAnswer4());
            ps.setString(6, qp.getCorrectAnswer());
            int res = ps.executeUpdate();
            rem-=res;
        }
        return rem==0;
    }
}
