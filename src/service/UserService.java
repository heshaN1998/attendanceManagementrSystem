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
public class UserService {
    private UserDAO userDAO = new UserDAO();
    
    public User login(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return userDAO.authenticate(username, password);
    }
}
