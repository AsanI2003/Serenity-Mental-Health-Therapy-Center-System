package com.project.serenity.controller;

import com.jfoenix.controls.JFXTextField;
import com.project.serenity.bo.BOFactory;
import com.project.serenity.bo.custom.PatientBO;
import com.project.serenity.dto.PatientDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class PatientController {

    @FXML
    private Button paClearbutton;

    @FXML
    private Button paDeletebutton;

    @FXML
    private Button paSavebutton;

    @FXML
    private Button paUpdatebutton;

    @FXML
    private TableColumn<PatientDto, Integer> patientID;

    @FXML
    private TableColumn<PatientDto, String> patientName;

    @FXML
    private TableColumn<PatientDto, String> patientPhone;

    @FXML
    private TableView<PatientDto> patientTable;

    @FXML
    private Label patientnameError;

    @FXML
    private JFXTextField patientnameField;

    @FXML
    private Label patientphoneError;

    @FXML
    private JFXTextField patientphoneField;

    private final PatientBO patientBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);

    @FXML
    void initialize() {
        patientID.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getPatientId()).asObject());
        patientName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        patientPhone.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPhone()));
        loadAllPatients();
    }

    private void loadAllPatients() {
        try {
            ObservableList<PatientDto> list = FXCollections.observableArrayList(patientBO.getAllPatients());
            patientTable.setItems(list);
        } catch (Exception e) {
            showAlert("Failed to load patients.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void BtnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        patientnameField.clear();
        patientphoneField.clear();
        patientnameError.setText("");
        patientphoneError.setText("");
        patientTable.getSelectionModel().clearSelection();
    }

    private boolean validateFields() {
        boolean isValid = true;

        String name = patientnameField.getText().trim();
        String phone = patientphoneField.getText().trim();

        if (name.isEmpty()) {
            patientnameError.setText("Name is required.");
            isValid = false;
        } else if (!name.matches("^[A-Za-z. ]+$")) {
            patientnameError.setText("letters and . only Ex :- A.G.Asan Indusara");
            isValid = false;
        } else {
            patientnameError.setText("");
        }

        if (phone.isEmpty()) {
            patientphoneError.setText("Phone is required.");
            isValid = false;
        } else if (!phone.matches("07\\d{8}")) {
            patientphoneError.setText("start with 07,no spaces and 10 digits");
            isValid = false;
        } else {
            patientphoneError.setText("");
        }

        return isValid;
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        PatientDto selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a patient from the table to delete.", Alert.AlertType.WARNING);
            return;
        }

        try {
            patientBO.deletePatient(selected.getPatientId());
            loadAllPatients();
            clearFields();
            showAlert("Patient deleted!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Delete failed.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        if (!validateFields()) {
            showAlert("Please correct the highlighted fields.", Alert.AlertType.WARNING);
            return;
        }

        try {
            String name = patientnameField.getText().trim();
            String phone = patientphoneField.getText().trim();

            PatientDto dto = new PatientDto(0, name, phone);
            patientBO.savePatient(dto);
            loadAllPatients();
            clearFields();
            showAlert("Patient saved!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Save failed.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        PatientDto selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a patient from the table to update.", Alert.AlertType.WARNING);
            return;
        }

        if (!validateFields()) {
            showAlert("Please correct the highlighted fields.", Alert.AlertType.WARNING);
            return;
        }

        try {
            selected.setName(patientnameField.getText().trim());
            selected.setPhone(patientphoneField.getText().trim());
            patientBO.updatePatient(selected);
            loadAllPatients();
            clearFields();
            showAlert("Patient updated!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Update failed.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void OnTableClick(MouseEvent event) {
        PatientDto selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            patientnameField.setText(selected.getName());
            patientphoneField.setText(selected.getPhone());
        }
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
