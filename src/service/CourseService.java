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
class CourseService {
    private CourseDAO courseDAO = new CourseDAO();
    
    public boolean addCourse(Course course) {
        if (course.getCode() == null || course.getName() == null) {
            throw new IllegalArgumentException("Course code and name required");
        }
        return courseDAO.create(course);
    }
    
    public List<Course> getAllCourses() {
        return courseDAO.getAll();
    }
    
    public boolean updateCourse(Course course) {
        if (course.getId() <= 0) {
            throw new IllegalArgumentException("Invalid course ID");
        }
        return courseDAO.update(course);
    }
    
    public boolean deleteCourse(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid course ID");
        }
        return courseDAO.delete(id);
    }
}
