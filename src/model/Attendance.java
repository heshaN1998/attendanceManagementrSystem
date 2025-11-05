/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

class Attendance {
    private int id;
    private int studentId;
    private int classId;
    private String status; // PRESENT, ABSENT
    private LocalDateTime markedAt;
    
    public Attendance() {}
    
    public Attendance(int id, int studentId, int classId, String status, LocalDateTime markedAt) {
        this.id = id;
        this.studentId = studentId;
        this.classId = classId;
        this.status = status;
        this.markedAt = markedAt;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getMarkedAt() { return markedAt; }
    public void setMarkedAt(LocalDateTime markedAt) { this.markedAt = markedAt; }
}
