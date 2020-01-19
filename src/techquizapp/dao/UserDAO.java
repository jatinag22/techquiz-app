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
import techquizapp.pojo.UserPojo;

/**
 *
 * @author agraw
 */
public class UserDAO {
    public static boolean validateUser(UserPojo user) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from users where userid = ? and password = ? and usertype = ?");
        ps.setString(1, user.getUserId());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getUserType());
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    
    public static boolean addStudent(UserPojo student) throws SQLException {
        Connection con = DBConnection.getConnection();
        boolean status = true;
        PreparedStatement ps = con.prepareStatement("select * from users where userid = ?");
        ps.setString(1, student.getUserId());
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            status = false;
        else {
            ps = con.prepareStatement("insert into users values(?,?,?)");
            ps.setString(1, student.getUserId());
            ps.setString(2, student.getPassword());
            ps.setString(3, student.getUserType());
            int res = ps.executeUpdate();
            status = res==1;
        }
        return status;
    }
    
    public static void changePassword(UserPojo user) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("update users set password=? where userid=?");
        ps.setString(1, user.getPassword());
        ps.setString(2, user.getUserId());
        ps.executeUpdate();
    }
    
}
