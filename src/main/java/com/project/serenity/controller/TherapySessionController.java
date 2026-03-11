package com.project.serenity.controller;

import com.jfoenix.controls.JFXTextField;
import com.project.serenity.bo.BOFactory;
import com.project.serenity.bo.custom.TherapySessionBO;
import com.project.serenity.dto.TherapySessionDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TherapySessionController {

    @FXML
    private Label bookError, patientidError, paymentidError, programidError, ssdurationError, therapistidError;

    @FXML
    private JFXTextField bookField, patientidField, paymentidField, programidField, ssdurationField, therapistidField;

    @FXML
    private TableView<TherapySessionDto> sessionTable;

    @FXML
    private TableColumn<TherapySessionDto, Integer> sessionID, sspatientID, sspaymentID, ssprogramID, sstherapistID;

    @FXML
    private TableColumn<TherapySessionDto, String> sessionDuration;

    @FXML
    private TableColumn<TherapySessionDto, LocalDateTime> bookedAt;

    @FXML
    private Button ssClearbutton, ssDeletebutton, ssSavebutton, ssSearchbutton, ssUpdatebutton, sstSearchbutton;

    private TherapySessionBO therapySessionBO = (TherapySessionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPYSESSION);

    private ObservableList<TherapySessionDto> obList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        sessionID.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getSessionId()).asObject());
        bookedAt.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getBookedat()));
        sessionDuration.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getDuration()));
        sstherapistID.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getTherapistId()).asObject());
        sspatientID.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getPatientId()).asObject());
        ssprogramID.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getProgramId()).asObject());
        sspaymentID.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getPaymentId()).asObject());

        loadAllTherapySessions();
    }

    private void loadAllTherapySessions() {
        obList.clear();
        try {
            List<TherapySessionDto> allSessions = therapySessionBO.getAllTherapySessions();
            obList.addAll(allSessions);
            sessionTable.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BtnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        bookField.clear();
        ssdurationField.clear();
        therapistidField.clear();
        patientidField.clear();
        programidField.clear();
        paymentidField.clear();
        clearErrorLabels();
        sessionTable.getSelectionModel().clearSelection();
    }

    private void clearErrorLabels() {
        bookError.setText("");
        ssdurationError.setText("");
        therapistidError.setText("");
        patientidError.setText("");
        programidError.setText("");
        paymentidError.setText("");
    }

    private boolean validateFields(boolean isUpdate) {
        boolean isValid = true;
        clearErrorLabels();

        if (bookField.getText().isEmpty()) {
            bookError.setText("Booking time required");
            isValid = false;
        } else {
            try {
                LocalDateTime.parse(bookField.getText());
            } catch (DateTimeParseException e) {
                bookError.setText("Invalid DateTime (yyyy-MM-ddTHH:mm)");
                isValid = false;
            }
        }

        if (ssdurationField.getText().isEmpty()) {
            ssdurationError.setText("Duration required");
            isValid = false;
        }

        if (therapistidField.getText().isEmpty()) {
            therapistidError.setText("Therapist ID required");
            isValid = false;
        } else {
            try {
                Integer.parseInt(therapistidField.getText());
            } catch (NumberFormatException e) {
                therapistidError.setText("Invalid ID");
                isValid = false;
            }
        }

        if (patientidField.getText().isEmpty()) {
            patientidError.setText("Patient ID required");
            isValid = false;
        } else {
            try {
                Integer.parseInt(patientidField.getText());
            } catch (NumberFormatException e) {
                patientidError.setText("Invalid ID");
                isValid = false;
            }
        }

        if (programidField.getText().isEmpty()) {
            programidError.setText("Program ID required");
            isValid = false;
        } else {
            try {
                Integer.parseInt(programidField.getText());
            } catch (NumberFormatException e) {
                programidError.setText("Invalid ID");
                isValid = false;
            }
        }

        if (isUpdate) {
            if (paymentidField.getText().isEmpty()) {
                paymentidError.setText("Session ID required for update");
                isValid = false;
            } else {
                try {
                    Integer.parseInt(paymentidField.getText());
                } catch (NumberFormatException e) {
                    paymentidError.setText("Invalid Session ID");
                    isValid = false;
                }
            }
        }

        return isValid;
    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        if (!validateFields(false)) return;

        try {
            LocalDateTime bookedAtTime = LocalDateTime.parse(bookField.getText());
            String duration = ssdurationField.getText();
            int therapistId = Integer.parseInt(therapistidField.getText());
            int patientId = Integer.parseInt(patientidField.getText());
            int programId = Integer.parseInt(programidField.getText());

            TherapySessionDto dto = new TherapySessionDto(0, bookedAtTime, duration, therapistId, patientId, programId, 0);

            boolean isSaved = therapySessionBO.saveTherapySession(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Therapy Session Saved!").show();
                loadAllTherapySessions();
                clearFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Save Session!").show();
        }
    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        if (!validateFields(true)) return;

        try {
            int sessionId = Integer.parseInt(paymentidField.getText());
            LocalDateTime bookedAtTime = LocalDateTime.parse(bookField.getText());
            String duration = ssdurationField.getText();
            int therapistId = Integer.parseInt(therapistidField.getText());
            int patientId = Integer.parseInt(patientidField.getText());
            int programId = Integer.parseInt(programidField.getText());

            TherapySessionDto dto = new TherapySessionDto(sessionId, bookedAtTime, duration, therapistId, patientId, programId, 0);

            boolean isUpdated = therapySessionBO.updateTherapySession(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Therapy Session Updated!").show();
                loadAllTherapySessions();
                clearFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Update Session!").show();
        }
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        TherapySessionDto selected = sessionTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                boolean isDeleted = therapySessionBO.deleteTherapySession(selected.getSessionId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Therapy Session Deleted!").show();
                    loadAllTherapySessions();
                    clearFields();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to Delete Session!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a record to delete!").show();
        }
    }

    @FXML
    void BtnpatientSearchOnAction(ActionEvent event) {
        try {
            int patientId = Integer.parseInt(patientidField.getText());
            List<TherapySessionDto> sessions = therapySessionBO.searchTherapySessionsByPatientId(patientId);

            highlightSessionsInTable(sessions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BtntherapistSearchOnAction(ActionEvent event) {
        try {
            int therapistId = Integer.parseInt(therapistidField.getText());
            List<TherapySessionDto> sessions = therapySessionBO.searchTherapySessionsByTherapistId(therapistId);

            highlightSessionsInTable(sessions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void highlightSessionsInTable(List<TherapySessionDto> matchedSessions) {
        if (matchedSessions.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "No matching sessions found.").show();
            return;
        }

        sessionTable.getSelectionModel().clearSelection();
        for (TherapySessionDto dto : matchedSessions) {
            for (int i = 0; i < sessionTable.getItems().size(); i++) {
                if (sessionTable.getItems().get(i).getSessionId() == dto.getSessionId()) {
                    sessionTable.getSelectionModel().select(i);
                }
            }
        }
    }

    @FXML
    void OnTableClick(MouseEvent event) {
        TherapySessionDto selected = sessionTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            paymentidField.setText(String.valueOf(selected.getSessionId()));
            bookField.setText(selected.getBookedat().toString());
            ssdurationField.setText(selected.getDuration());
            therapistidField.setText(String.valueOf(selected.getTherapistId()));
            patientidField.setText(String.valueOf(selected.getPatientId()));
            programidField.setText(String.valueOf(selected.getProgramId()));
        }
    }

}
