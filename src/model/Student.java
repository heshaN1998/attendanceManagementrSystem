/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


/**
 *
 * @author ACER
 */
class Student {
    private int id;
    private String regNo;
    private String name;
    private String email;
    private String phone;
    private int courseId;
    
    public Student() {}
    
    public Student(int id, String regNo, String name, String email, String phone, int courseId) {
        this.id = id;
        this.regNo = regNo;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.courseId = courseId;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
}
