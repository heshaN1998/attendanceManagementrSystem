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
public class ClassScheduleService {
    private ClassScheduleDAO scheduleDAO = new ClassScheduleDAO();
    
    public boolean createSchedule(ClassSchedule schedule) {
        if (schedule.getCourseId() <= 0 || schedule.getLecturerId() <= 0) {
            throw new IllegalArgumentException("Valid course and lecturer required");
        }
        return scheduleDAO.create(schedule);
    }
    
    public List<ClassSchedule> getAllSchedules() {
        return scheduleDAO.getAll();
    }
}
