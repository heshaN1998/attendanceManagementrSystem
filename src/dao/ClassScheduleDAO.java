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
public class ClassScheduleDAO {
    public boolean create(ClassSchedule cs) {
        String sql = "INSERT INTO class_schedules (course_id, lecturer_id, subject, date, time) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cs.getCourseId());
            ps.setInt(2, cs.getLecturerId());
            ps.setString(3, cs.getSubject());
            ps.setDate(4, Date.valueOf(cs.getDate()));
            ps.setString(5, cs.getTime());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<ClassSchedule> getAll() {
        List<ClassSchedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM class_schedules";
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                schedules.add(new ClassSchedule(rs.getInt("id"), rs.getInt("course_id"),
                              rs.getInt("lecturer_id"), rs.getString("subject"),
                              rs.getDate("date").toLocalDate(), rs.getString("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }
}
