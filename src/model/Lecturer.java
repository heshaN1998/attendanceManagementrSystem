/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


public class Lecturer {
    private int id;
    private String empNo;
    private String name;
    private String email;
    private String phone;
    private String subject;
    
    public Lecturer() {}
    
    public Lecturer(int id, String empNo, String name, String email, String phone, String subject) {
        this.id = id;
        this.empNo = empNo;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.subject = subject;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmpNo() { return empNo; }
    public void setEmpNo(String empNo) { this.empNo = empNo; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}
