/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

/**
 *
 * @author ACER
 */

import model.User;
import service.UserService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginUI extends Application {
    private UserService userService = new UserService();
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("AMS - Login");
        
        // Create UI components
        Label titleLabel = new Label("Student Attendance Management System");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        userField.setPromptText("Enter username");
        
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter password");
        
        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(100);
        
        Label msgLabel = new Label();
        msgLabel.setStyle("-fx-text-fill: red;");
        
        // Layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));
        
        grid.add(titleLabel, 0, 0, 2, 1);
        grid.add(userLabel, 0, 1);
        grid.add(userField, 1, 1);
        grid.add(passLabel, 0, 2);
        grid.add(passField, 1, 2);
        grid.add(loginBtn, 1, 3);
        grid.add(msgLabel, 0, 4, 2, 1);
        
        // Event handling
        loginBtn.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();
            
            try {
                User user = userService.login(username, password);
                if (user != null) {
                    msgLabel.setStyle("-fx-text-fill: green;");
                    msgLabel.setText("Login successful!");
                    
                    // Open dashboard based on role
                    if (user.getRole().equals("ADMIN")) {
                        new AdminDashboardUI().start(new Stage());
                    } else if (user.getRole().equals("LECTURER")) {
                        new LecturerDashboardUI().start(new Stage());
                    }
                    stage.close();
                } else {
                    msgLabel.setStyle("-fx-text-fill: red;");
                    msgLabel.setText("Invalid credentials!");
                }
            } catch (Exception ex) {
                msgLabel.setText("Error: " + ex.getMessage());
            }
        });
        
        Scene scene = new Scene(grid, 500, 350);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
