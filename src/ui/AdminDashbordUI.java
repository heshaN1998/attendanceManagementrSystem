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

public class AdminDashboardUI extends Application {
    private CourseService courseService = new CourseService();
    private StudentService studentService = new StudentService();
    private LecturerService lecturerService = new LecturerService();
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("AMS - Admin Dashboard");
        
        TabPane tabPane = new TabPane();
        
        // Courses Tab
        Tab coursesTab = new Tab("Courses", createCoursesPane());
        coursesTab.setClosable(false);
        
        // Students Tab
        Tab studentsTab = new Tab("Students", createStudentsPane());
        studentsTab.setClosable(false);
        
        // Lecturers Tab
        Tab lecturersTab = new Tab("Lecturers", createLecturersPane());
        lecturersTab.setClosable(false);
        
        tabPane.getTabs().addAll(coursesTab, studentsTab, lecturersTab);
        
        Scene scene = new Scene(tabPane, 900, 600);
        stage.setScene(scene);
        stage.show();
    }
    
    private VBox createCoursesPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        
        // Input fields
        TextField codeField = new TextField();
        codeField.setPromptText("Course Code");
        TextField nameField = new TextField();
        nameField.setPromptText("Course Name");
        TextField descField = new TextField();
        descField.setPromptText("Description");
        
        HBox inputBox = new HBox(10, codeField, nameField, descField);
        
        // Buttons
        Button addBtn = new Button("Add Course");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");
        Button refreshBtn = new Button("Refresh");
        
        HBox btnBox = new HBox(10, addBtn, updateBtn, deleteBtn, refreshBtn);
        
        // Table
        TableView<Course> table = new TableView<>();
        TableColumn<Course, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Course, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<Course, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Course, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        table.getColumns().addAll(idCol, codeCol, nameCol, descCol);
        
        Label msgLabel = new Label();
        
        // Events
        addBtn.setOnAction(e -> {
            Course c = new Course(0, codeField.getText(), nameField.getText(), descField.getText());
            if (courseService.addCourse(c)) {
                msgLabel.setText("Course added successfully!");
                refreshTable(table);
                clearFields(codeField, nameField, descField);
            } else {
                msgLabel.setText("Failed to add course!");
            }
        });
        
        refreshBtn.setOnAction(e -> refreshTable(table));
        
        table.setOnMouseClicked(e -> {
            Course selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                codeField.setText(selected.getCode());
                nameField.setText(selected.getName());
                descField.setText(selected.getDescription());
            }
        });
        
        updateBtn.setOnAction(e -> {
            Course selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.setCode(codeField.getText());
                selected.setName(nameField.getText());
                selected.setDescription(descField.getText());
                if (courseService.updateCourse(selected)) {
                    msgLabel.setText("Course updated successfully!");
                    refreshTable(table);
                }
            }
        });
        
        deleteBtn.setOnAction(e -> {
            Course selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                if (courseService.deleteCourse(selected.getId())) {
                    msgLabel.setText("Course deleted successfully!");
                    refreshTable(table);
                    clearFields(codeField, nameField, descField);
                }
            }
        });
        
        refreshTable(table);
        
        vbox.getChildren().addAll(inputBox, btnBox, table, msgLabel);
        return vbox;
    }
    
    private VBox createStudentsPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        
        // Input fields
        TextField regField = new TextField();
        regField.setPromptText("Registration No");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        TextField courseIdField = new TextField();
        courseIdField.setPromptText("Course ID");
        
        HBox inputBox1 = new HBox(10, regField, nameField, emailField);
        HBox inputBox2 = new HBox(10, phoneField, courseIdField);
        
        // Buttons
        Button addBtn = new Button("Register Student");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");
        Button refreshBtn = new Button("Refresh");
        
        HBox btnBox = new HBox(10, addBtn, updateBtn, deleteBtn, refreshBtn);
        
        // Table
        TableView<Student> table = new TableView<>();
        TableColumn<Student, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Student, String> regCol = new TableColumn<>("Reg No");
        regCol.setCellValueFactory(new PropertyValueFactory<>("regNo"));
        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Student, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Student, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        TableColumn<Student, Integer> courseCol = new TableColumn<>("Course ID");
        courseCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        
        table.getColumns().addAll(idCol, regCol, nameCol, emailCol, phoneCol, courseCol);
        
        Label msgLabel = new Label();
        
        // Events
        addBtn.setOnAction(e -> {
            Student s = new Student(0, regField.getText(), nameField.getText(),
                                   emailField.getText(), phoneField.getText(),
                                   Integer.parseInt(courseIdField.getText()));
            if (studentService.registerStudent(s)) {
                msgLabel.setText("Student registered successfully!");
                refreshStudentTable(table);
                clearFields(regField, nameField, emailField, phoneField, courseIdField);
            }
        });
        
        refreshBtn.setOnAction(e -> refreshStudentTable(table));
        
        table.setOnMouseClicked(e -> {
            Student selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                regField.setText(selected.getRegNo());
                nameField.setText(selected.getName());
                emailField.setText(selected.getEmail());
                phoneField.setText(selected.getPhone());
                courseIdField.setText(String.valueOf(selected.getCourseId()));
            }
        });
        
        updateBtn.setOnAction(e -> {
            Student selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.setRegNo(regField.getText());
                selected.setName(nameField.getText());
                selected.setEmail(emailField.getText());
                selected.setPhone(phoneField.getText());
                selected.setCourseId(Integer.parseInt(courseIdField.getText()));
                if (studentService.updateStudent(selected)) {
                    msgLabel.setText("Student updated successfully!");
                    refreshStudentTable(table);
                }
            }
        });
        
        deleteBtn.setOnAction(e -> {
            Student selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                if (studentService.deleteStudent(selected.getId())) {
                    msgLabel.setText("Student deleted successfully!");
                    refreshStudentTable(table);
                    clearFields(regField, nameField, emailField, phoneField, courseIdField);
                }
            }
        });
        
        refreshStudentTable(table);
        
        vbox.getChildren().addAll(inputBox1, inputBox2, btnBox, table, msgLabel);
        return vbox;
    }
    
    private VBox createLecturersPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        
        TextField empField = new TextField();
        empField.setPromptText("Employee No");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        TextField subjectField = new TextField();
        subjectField.setPromptText("Subject");
        
        HBox inputBox1 = new HBox(10, empField, nameField, emailField);
        HBox inputBox2 = new HBox(10, phoneField, subjectField);
        
        Button addBtn = new Button("Add Lecturer");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");
        Button refreshBtn = new Button("Refresh");
        
        HBox btnBox = new HBox(10, addBtn, updateBtn, deleteBtn, refreshBtn);
        
        TableView<Lecturer> table = new TableView<>();
        TableColumn<Lecturer, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Lecturer, String> empCol = new TableColumn<>("Emp No");
        empCol.setCellValueFactory(new PropertyValueFactory<>("empNo"));
        TableColumn<Lecturer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Lecturer, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Lecturer, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        TableColumn<Lecturer, String> subjectCol = new TableColumn<>("Subject");
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        
        table.getColumns().addAll(idCol, empCol, nameCol, emailCol, phoneCol, subjectCol);
        
        Label msgLabel = new Label();
        
        addBtn.setOnAction(e -> {
            Lecturer l = new Lecturer(0, empField.getText(), nameField.getText(),
                                     emailField.getText(), phoneField.getText(),
                                     subjectField.getText());
            if (lecturerService.addLecturer(l)) {
                msgLabel.setText("Lecturer added successfully!");
                refreshLecturerTable(table);
                clearFields(empField, nameField, emailField, phoneField, subjectField);
            }
        });
        
        refreshBtn.setOnAction(e -> refreshLecturerTable(table));
        
        refreshLecturerTable(table);
        
        vbox.getChildren().addAll(inputBox1, inputBox2, btnBox, table, msgLabel);
        return vbox;
    }
    
    private void refreshTable(TableView<Course> table) {
        ObservableList<Course> data = FXCollections.observableArrayList(courseService.getAllCourses());
        table.setItems(data);
    }
    
    private void refreshStudentTable(TableView<Student> table) {
        ObservableList<Student> data = FXCollections.observableArrayList(studentService.getAllStudents());
        table.setItems(data);
    }
    
    private void refreshLecturerTable(TableView<Lecturer> table) {
        ObservableList<Lecturer> data = FXCollections.observableArrayList(lecturerService.getAllLecturers());
        table.setItems(data);
    }
    
    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
}
