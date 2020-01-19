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
import java.sql.Statement;
import java.util.ArrayList;
import techquizapp.dbutil.DBConnection;
import techquizapp.pojo.ExamPojo;

/**
 *
 * @author agraw
 */
public class ExamDAO {
    public static String getExamId() throws SQLException {
        int rowCount = 0;
        Connection con =DBConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select count(*) as totalrows from exam");
        if(rs.next())
            rowCount = rs.getInt(1);
        return "EX-"+(rowCount+1);
    }
    
    public static boolean addExam(ExamPojo newExam) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("insert into exam values(?,?,?)");
        ps.setString(1,newExam.getExamId());
        ps.setString(2,newExam.getLanguage());
        ps.setInt(3, newExam.getTotalQuestions());
        int res = ps.executeUpdate();
        return res==1;
    }
    
    public static ArrayList<String> findExamId(String language) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("select examid from exam where language=?");
        ps.setString(1, language);
        ResultSet rs = ps.executeQuery();
        ArrayList<String> examIdList = new ArrayList<>();
        while(rs.next()) {
            examIdList.add(rs.getString(1));
        }
        return examIdList;
    }
    
    public static boolean isPaperSet(String subject) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("select examid from exam where language=?");
        ps.setString(1, subject);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    
    public static ArrayList<String> getExamIdBySubject(String userId, String subject) throws SQLException {
        String qry = "select examid from exam where language=? minus select examid from performance where userid=?";
        //String qry = "select examid from exam where examid not in (select examid from performance where userid=?)";
        ArrayList<String> examIdList = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(qry);
        ps.setString(1, subject);
        ps.setString(2, userId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            examIdList.add(rs.getString(1));
        }
        return examIdList;
    }
    
    public static int getQuestionCountByExam(String examId) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("select total_question from exam where examid=?");
        ps.setString(1, examId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    
}
