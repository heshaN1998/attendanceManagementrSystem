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
public class StudentService {
    private StudentDAO studentDAO = new StudentDAO();
    
    public boolean registerStudent(Student student) {
        if (student.getRegNo() == null || student.getName() == null) {
            throw new IllegalArgumentException("Registration number and name required");
        }
        return studentDAO.create(student);
    }
    
    public List<Student> getAllStudents() {
        return studentDAO.getAll();
    }
    
    public List<Student> getStudentsByCourse(int courseId) {
        return studentDAO.getByCourse(courseId);
    }
    
    public boolean updateStudent(Student student) {
        if (student.getId() <= 0) {
            throw new IllegalArgumentException("Invalid student ID");
        }
        return studentDAO.update(student);
    }
    
    public boolean deleteStudent(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid student ID");
        }
        return studentDAO.delete(id);
    }
}
