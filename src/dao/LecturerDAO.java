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
class LecturerDAO {
    public boolean create(Lecturer lecturer) {
        String sql = "INSERT INTO lecturers (emp_no, name, email, phone, subject) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lecturer.getEmpNo());
            ps.setString(2, lecturer.getName());
            ps.setString(3, lecturer.getEmail());
            ps.setString(4, lecturer.getPhone());
            ps.setString(5, lecturer.getSubject());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Lecturer> getAll() {
        List<Lecturer> lecturers = new ArrayList<>();
        String sql = "SELECT * FROM lecturers";
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lecturers.add(new Lecturer(rs.getInt("id"), rs.getString("emp_no"),
                             rs.getString("name"), rs.getString("email"),
                             rs.getString("phone"), rs.getString("subject")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lecturers;
    }
    
    public boolean update(Lecturer lecturer) {
        String sql = "UPDATE lecturers SET emp_no=?, name=?, email=?, phone=?, subject=? WHERE id=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lecturer.getEmpNo());
            ps.setString(2, lecturer.getName());
            ps.setString(3, lecturer.getEmail());
            ps.setString(4, lecturer.getPhone());
            ps.setString(5, lecturer.getSubject());
            ps.setInt(6, lecturer.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM lecturers WHERE id=?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
