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
import techquizapp.pojo.PerformancePojo;
import techquizapp.pojo.StudentScore;

/**
 *
 * @author agraw
 */
public class PerformanceDAO {

    public static ArrayList<String> getAllStudentId() throws SQLException {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        ArrayList<String> studentList = new ArrayList<>();
        ResultSet rs = st.executeQuery("select distinct userid from performance");
        while (rs.next()) {
            studentList.add(rs.getString(1));
        }
        return studentList;
    }

    public static ArrayList<String> getAllExamId(String userId) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("select examid from performance where userid=?");
        ps.setString(1, userId);
        ResultSet rs = ps.executeQuery();
        ArrayList<String> examIdList = new ArrayList<>();
        while (rs.next()) {
            examIdList.add(rs.getString(1));
        }
        return examIdList;
    }
    public static StudentScore getScore(String userId, String examId) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("select language, per from performance where userid=? and examid=?");
        ps.setString(1, userId);
        ps.setString(2, examId);
        ResultSet rs = ps.executeQuery();
        StudentScore ss = new StudentScore();
        rs.next();
        ss.setLanguage(rs.getString(1));
        ss.setPer(rs.getDouble(2));
        return ss;
    }
    
    public static void addPerformance(PerformancePojo performance) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("insert into performance values(?,?,?,?,?,?,?)");
        ps.setString(1, performance.getUserId());
        ps.setString(2, performance.getExamId());
        ps.setInt(3, performance.getRight());
        ps.setInt(4, performance.getWrong());
        ps.setInt(5, performance.getUnattempted());
        ps.setDouble(6, performance.getPer());
        ps.setString(7, performance.getLanguage());
        ps.executeUpdate();
    }
    
    public static ArrayList<PerformancePojo> getAllData() throws SQLException {
        ArrayList<PerformancePojo> performanceList = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from performance");
        while(rs.next()) {
            String userId = rs.getString(1);
            String examId = rs.getString(2);
            int right = rs.getInt(3);
            int wrong = rs.getInt(4);
            int unattempted = rs.getInt(5);
            double per = rs.getDouble(6);
            String language = rs.getString(7);
            PerformancePojo p = new PerformancePojo(userId, examId, right, wrong, unattempted, per, language);
            performanceList.add(p);
        }
        return performanceList;
    }
}
