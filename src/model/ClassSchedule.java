/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.time.LocalDate;


 public class ClassSchedule {
    private int id;
    private int courseId;
    private int lecturerId;
    private String subject;
    private LocalDate date;
    private String time;
    
    public ClassSchedule() {}
    
    public ClassSchedule(int id, int courseId, int lecturerId, String subject, LocalDate date, String time) {
        this.id = id;
        this.courseId = courseId;
        this.lecturerId = lecturerId;
        this.subject = subject;
        this.date = date;
        this.time = time;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public int getLecturerId() { return lecturerId; }
    public void setLecturerId(int lecturerId) { this.lecturerId = lecturerId; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}