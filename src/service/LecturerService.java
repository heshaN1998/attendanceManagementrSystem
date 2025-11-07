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
public class LecturerService {
    private LecturerDAO lecturerDAO = new LecturerDAO();
    
    public boolean addLecturer(Lecturer lecturer) {
        if (lecturer.getEmpNo() == null || lecturer.getName() == null) {
            throw new IllegalArgumentException("Employee number and name required");
        }
        return lecturerDAO.create(lecturer);
    }
    
    public List<Lecturer> getAllLecturers() {
        return lecturerDAO.getAll();
    }
    
    public boolean updateLecturer(Lecturer lecturer) {
        if (lecturer.getId() <= 0) {
            throw new IllegalArgumentException("Invalid lecturer ID");
        }
        return lecturerDAO.update(lecturer);
    }
    
    public boolean deleteLecturer(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid lecturer ID");
        }
        return lecturerDAO.delete(id);
    }
}