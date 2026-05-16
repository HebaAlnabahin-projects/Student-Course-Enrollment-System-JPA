package Controllers;

import DAO.EnrollmentDAO;
import Models.Enrollment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EnrollmentController {

    @FXML private TextField studentIdField;
    @FXML private TextField courseIdField;
    @FXML private DatePicker datePicker;
    @FXML private TableView<Enrollment> enrollmentTable;
    @FXML private TableColumn<Enrollment, Integer> colStudentId;
    @FXML private TableColumn<Enrollment, Integer> colCourseId;
    @FXML private TableColumn<Enrollment, LocalDate> colDate;

    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    @FXML
    public void initialize() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("enrollmentDate"));
    }

    @FXML
    private void handleView() {
        try {
            List<Enrollment> list = enrollmentDAO.getAllEnrollments();
            ObservableList<Enrollment> data = FXCollections.observableArrayList(list);
            enrollmentTable.setItems(data);
            if (list.isEmpty()) {
                showAlert("Information", "No records found.");
            }
        } catch (SQLException e) {
            showAlert("Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleAdd() {
        try {
            int sid = Integer.parseInt(studentIdField.getText());
            int cid = Integer.parseInt(courseIdField.getText());
            LocalDate date = datePicker.getValue();

            if (date == null) throw new Exception("Please select a date.");

            Enrollment enrollment = new Enrollment(sid, cid, date);
            enrollmentDAO.addEnrollment(enrollment);
            handleView();
            clearFields();
            showAlert("Success", "Enrollment added successfully.");
        } catch (Exception e) {
            showAlert("Input Error", "Please check your inputs: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        try {
            int sid = Integer.parseInt(studentIdField.getText());
            int cid = Integer.parseInt(courseIdField.getText());
            LocalDate date = datePicker.getValue();

            if (date == null) throw new Exception("Please select a date.");

            Enrollment enrollment = new Enrollment(sid, cid, date);
            enrollmentDAO.updateEnrollment(enrollment);
            handleView();
            showAlert("Success", "Enrollment updated successfully.");
        } catch (Exception e) {
            showAlert("Update Error", e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Enrollment selected = enrollmentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                enrollmentDAO.deleteEnrollment(selected.getStudentId(), selected.getCourseId());
                handleView();
                showAlert("Success", "Enrollment deleted successfully.");
            } catch (SQLException e) {
                showAlert("Delete Error", e.getMessage());
            }
        } else {
            showAlert("Selection Required", "Please select a record from the table.");
        }
    }

    private void clearFields() {
        studentIdField.clear();
        courseIdField.clear();
        datePicker.setValue(null);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION
        );
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}