/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import dao.*;
import model.*;
import java.util.List;

/**
 *
 * @author ACER
 */
class AttendanceService {
    private AttendanceDAO attendanceDAO = new AttendanceDAO();
    
    public boolean markAttendance(Attendance attendance) {
        if (attendance.getStudentId() <= 0 || attendance.getClassId() <= 0) {
            throw new IllegalArgumentException("Valid student and class required");
        }
        if (!attendance.getStatus().equals("PRESENT") && !attendance.getStatus().equals("ABSENT")) {
            throw new IllegalArgumentException("Status must be PRESENT or ABSENT");
        }
        return attendanceDAO.markAttendance(attendance);
    }
    
    public List<Attendance> getClassAttendance(int classId) {
        return attendanceDAO.getByClass(classId);
    }
    
    public List<Attendance> getStudentAttendance(int studentId) {
        return attendanceDAO.getByStudent(studentId);
    }
    
    public double calculateAttendancePercentage(int studentId) {
        List<Attendance> records = attendanceDAO.getByStudent(studentId);
        if (records.isEmpty()) return 0.0;
        
        long present = records.stream()
                             .filter(a -> a.getStatus().equals("PRESENT"))
                             .count();
        return (present * 100.0) / records.size();
    }
}
