/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DBConfig;
import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class AttendanceDAO {
    public boolean markAttendance(Attendance att) {
        String sql = "INSERT INTO attendance (student_id, class_id, status, marked_at) VALUES (?,?,?,?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, att.getStudentId());
            ps.setInt(2, att.getClassId());
            ps.setString(3, att.getStatus());
            ps.setTimestamp(4, Timestamp.valueOf(att.getMarkedAt()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Attendance> getByClass(int classId) {
        List<Attendance> records = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE class_id=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, classId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                records.add(new Attendance(rs.getInt("id"), rs.getInt("student_id"),
                           rs.getInt("class_id"), rs.getString("status"),
                           rs.getTimestamp("marked_at").toLocalDateTime()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
    
    public List<Attendance> getByStudent(int studentId) {
        List<Attendance> records = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE student_id=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                records.add(new Attendance(rs.getInt("id"), rs.getInt("student_id"),
                           rs.getInt("class_id"), rs.getString("status"),
                           rs.getTimestamp("marked_at").toLocalDateTime()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}
