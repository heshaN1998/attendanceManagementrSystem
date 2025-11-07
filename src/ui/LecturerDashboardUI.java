/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

/**
 *
 * @author ACER
 */


import model.*;
import service.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.util.List;

public class LecturerDashboardUI extends Application {
    private AttendanceService attendanceService = new AttendanceService();
    private StudentService studentService = new StudentService();
    private ClassScheduleService scheduleService = new ClassScheduleService();
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("AMS - Lecturer Dashboard");
        
        TabPane tabPane = new TabPane();
        
        Tab markAttendanceTab = new Tab("Mark Attendance", createMarkAttendancePane());
        markAttendanceTab.setClosable(false);
        
        Tab viewReportsTab = new Tab("View Reports", createReportsPane());
        viewReportsTab.setClosable(false);
        
        tabPane.getTabs().addAll(markAttendanceTab, viewReportsTab);
        
        Scene scene = new Scene(tabPane, 900, 600);
        stage.setScene(scene);
        stage.show();
    }
    
    private VBox createMarkAttendancePane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        
        Label titleLabel = new Label("Mark Attendance");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Class selection
        TextField classIdField = new TextField();
        classIdField.setPromptText("Enter Class ID");
        TextField courseIdField = new TextField();
        courseIdField.setPromptText("Enter Course ID");
        
        Button loadStudentsBtn = new Button("Load Students");
        
        HBox selectionBox = new HBox(10, classIdField, courseIdField, loadStudentsBtn);
        
        // Student table with attendance marking
        TableView<Student> table = new TableView<>();
        TableColumn<Student, Integer> idCol = new TableColumn<>("Student ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Student, String> regCol = new TableColumn<>("Reg No");
        regCol.setCellValueFactory(new PropertyValueFactory<>("regNo"));
        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Student, Void> actionCol = new TableColumn<>("Mark Attendance");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button presentBtn = new Button("Present");
            private final Button absentBtn = new Button("Absent");
            private final HBox btnBox = new HBox(5, presentBtn, absentBtn);
            
            {
                presentBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                absentBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                
                presentBtn.setOnAction(e -> {
                    Student student = getTableView().getItems().get(getIndex());
                    markStudentAttendance(student, classIdField.getText(), "PRESENT");
                });
                
                absentBtn.setOnAction(e -> {
                    Student student = getTableView().getItems().get(getIndex());
                    markStudentAttendance(student, classIdField.getText(), "ABSENT");
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnBox);
            }
        });
        
        table.getColumns().addAll(idCol, regCol, nameCol, actionCol);
        
        Label msgLabel = new Label();
        
        // Load students event
        loadStudentsBtn.setOnAction(e -> {
            try {
                int courseId = Integer.parseInt(courseIdField.getText());
                List<Student> students = studentService.getStudentsByCourse(courseId);
                ObservableList<Student> data = FXCollections.observableArrayList(students);
                table.setItems(data);
                msgLabel.setText("Loaded " + students.size() + " students");
            } catch (NumberFormatException ex) {
                msgLabel.setText("Invalid course ID!");
            }
        });
        
        vbox.getChildren().addAll(titleLabel, selectionBox, table, msgLabel);
        return vbox;
    }
    
    private void markStudentAttendance(Student student, String classId, String status) {
        try {
            int clsId = Integer.parseInt(classId);
            Attendance att = new Attendance(0, student.getId(), clsId, status, LocalDateTime.now());
            
            if (attendanceService.markAttendance(att)) {
                showAlert("Success", "Attendance marked as " + status + " for " + student.getName());
            } else {
                showAlert("Error", "Failed to mark attendance!");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid class ID!");
        }
    }
    
    private VBox createReportsPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        
        Label titleLabel = new Label("Attendance Reports");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Filter options
        RadioButton byStudentBtn = new RadioButton("By Student");
        RadioButton byClassBtn = new RadioButton("By Class");
        ToggleGroup filterGroup = new ToggleGroup();
        byStudentBtn.setToggleGroup(filterGroup);
        byClassBtn.setToggleGroup(filterGroup);
        byStudentBtn.setSelected(true);
        
        TextField filterIdField = new TextField();
        filterIdField.setPromptText("Enter Student ID or Class ID");
        
        Button generateBtn = new Button("Generate Report");
        
        HBox filterBox = new HBox(10, byStudentBtn, byClassBtn, filterIdField, generateBtn);
        
        // Report table
        TableView<Attendance> table = new TableView<>();
        TableColumn<Attendance, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Attendance, Integer> studentIdCol = new TableColumn<>("Student ID");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        TableColumn<Attendance, Integer> classIdCol = new TableColumn<>("Class ID");
        classIdCol.setCellValueFactory(new PropertyValueFactory<>("classId"));
        TableColumn<Attendance, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        TableColumn<Attendance, LocalDateTime> dateCol = new TableColumn<>("Marked At");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("markedAt"));
        
        table.getColumns().addAll(idCol, studentIdCol, classIdCol, statusCol, dateCol);
        
        Label summaryLabel = new Label();
        summaryLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        // Generate report event
        generateBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(filterIdField.getText());
                List<Attendance> records;
                
                if (byStudentBtn.isSelected()) {
                    records = attendanceService.getStudentAttendance(id);
                    double percentage = attendanceService.calculateAttendancePercentage(id);
                    summaryLabel.setText(String.format("Attendance Percentage: %.2f%%", percentage));
                } else {
                    records = attendanceService.getClassAttendance(id);
                    long present = records.stream().filter(a -> a.getStatus().equals("PRESENT")).count();
                    summaryLabel.setText(String.format("Present: %d / Total: %d", present, records.size()));
                }
                
                ObservableList<Attendance> data = FXCollections.observableArrayList(records);
                table.setItems(data);
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid ID!");
            }
        });
        
        vbox.getChildren().addAll(titleLabel, filterBox, table, summaryLabel);
        return vbox;
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}