package com.project.serenity.controller;

import com.jfoenix.controls.JFXTextField;
import com.project.serenity.bo.BOFactory;
import com.project.serenity.bo.custom.TherapyProgramBO;
import com.project.serenity.dto.TherapyProgramDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lombok.experimental.NonFinal;

import java.util.List;

public class TherapyProgramController {

    @FXML
    private TableColumn<TherapyProgramDto, Double> Fee;

    @FXML
    private Label durationError;

    @FXML
    private Label feeError;

    @FXML
    private JFXTextField feeField;

    @FXML
    private Button pClearbutton;

    @FXML
    private Button pDeletebutton;

    @FXML
    private Button pSavebutton;

    @FXML
    private Button pUpdatebutton;

    @FXML
    private JFXTextField prdurationField;

    @FXML
    private Label prnameError;

    @FXML
    private JFXTextField prnameField;

    @FXML
    private TableColumn<TherapyProgramDto, String> programDuration;

    @FXML
    private TableColumn<TherapyProgramDto, Integer> programID;

    @FXML
    private TableColumn<TherapyProgramDto, String> programName;

    @FXML
    private TableView<TherapyProgramDto> programTable;

  private final TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPYPROGRAM);

    public void initialize() {
        programID.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getProgramId()).asObject());
        programName.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
        programDuration.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getDuration()));
        Fee.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getFee()).asObject());

        loadAllPrograms();
    }

    private void loadAllPrograms() {
        try {
            List<TherapyProgramDto> all = therapyProgramBO.getAllTherapyPrograms();
            ObservableList<TherapyProgramDto> list = FXCollections.observableArrayList(all);
            programTable.setItems(list);
        } catch (Exception e) {
            showAlert("Failed to load data.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void BtnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        TherapyProgramDto selected = programTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a program from table to delete.", Alert.AlertType.WARNING);
            return; }
        if (selected != null) {
            try {
                therapyProgramBO.deleteTherapyProgram(selected.getProgramId());
                showAlert("Program deleted!", Alert.AlertType.INFORMATION);
                clearFields();
                loadAllPrograms();
            } catch (Exception e) {
                showAlert("Delete failed.", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        if (!validateFields()){
            showAlert("Please correct the highlighted fields.", Alert.AlertType.WARNING);
            return;}

        try {
            TherapyProgramDto dto = new TherapyProgramDto(
                    0,
                    prnameField.getText(),
                    prdurationField.getText(),
                    Double.parseDouble(feeField.getText())
            );
            therapyProgramBO.saveTherapyProgram(dto);
            showAlert("Program saved!", Alert.AlertType.INFORMATION);
            clearFields();
            loadAllPrograms();
        } catch (Exception e) {
            showAlert("Save failed.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        TherapyProgramDto selected = programTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a program from table to update.", Alert.AlertType.WARNING);
            return;
        }
        if (selected != null && validateFields()) {
            try {
                selected.setName(prnameField.getText());
                selected.setDuration(prdurationField.getText());
                selected.setFee(Double.parseDouble(feeField.getText()));
                therapyProgramBO.updateTherapyProgram(selected);
                showAlert("Program updated!", Alert.AlertType.INFORMATION);
                clearFields();
                loadAllPrograms();
            } catch (Exception e) {
                showAlert("Update failed.", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    void OnTableClick(MouseEvent event) {
        TherapyProgramDto selected = programTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            prnameField.setText(selected.getName());
            prdurationField.setText(selected.getDuration());
            feeField.setText(String.valueOf(selected.getFee()));
        }
    }

    private void clearFields() {
        prnameField.clear();
        prdurationField.clear();
        feeField.clear();
        prnameError.setText("");
        durationError.setText("");
        feeError.setText("");
        programTable.getSelectionModel().clearSelection();
    }

    private boolean validateFields() {
        boolean valid = true;


        if (prnameField.getText().isEmpty()) {
            prnameError.setText("Name is required");
            valid = false;
        } else if (!prnameField.getText().matches("^[A-Za-z ]+$")) {
            prnameError.setText("letters Only");
            valid = false;
        } else {
            prnameError.setText("");
        }


        if (prdurationField.getText().isEmpty()) {
            durationError.setText("Duration is required");
            valid = false;
         }else {
            durationError.setText("");
        }


        if (feeField.getText().isEmpty()) {
            feeError.setText("Fee is required");
            valid = false;
        } else if (!feeField.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
            feeError.setText("Enter a valid amount Ex :- 100 or 99.99");
            valid = false;
        } else {
            feeError.setText("");
        }

        return valid;
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
