package com.project.serenity.controller;

import com.jfoenix.controls.JFXTextField;
import com.project.serenity.bo.BOFactory;
import com.project.serenity.bo.custom.TherapistBO;
import com.project.serenity.dto.TherapistDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class TherapistController {

    @FXML
    private TableColumn<TherapistDto, String> availability;

    @FXML
    private JFXTextField availabilityField;

    @FXML
    private TableColumn<TherapistDto, String> specialization;

    @FXML
    private JFXTextField specializationField;

    @FXML
    private Button tDeletebutton;

    @FXML
    private Button tSavebutton;

    @FXML
    private Button tUpdatebutton;

    @FXML
    private Button clearbutton;

    @FXML
    private TableColumn<TherapistDto, Integer> therapistID;

    @FXML
    private TableColumn<TherapistDto, String> therapistName;

    @FXML
    private TableColumn<TherapistDto, Integer> therapistPhone;

    @FXML
    private TableView<TherapistDto> therapistTable;

    @FXML
    private JFXTextField tnameField;

    @FXML
    private JFXTextField tphoneField;

    @FXML
    private Label phoneError;

    @FXML
    private Label availabilityError;

    @FXML
    private Label nameError;

    @FXML
    private Label specializationError;

  private final TherapistBO therapistBO = (TherapistBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THEPAPIST);

    public void initialize() {
        therapistID.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getTherapistId()).asObject());
        therapistName.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
        therapistPhone.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(Math.toIntExact(cell.getValue().getPhone())).asObject());
        specialization.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getSpecialization()));
        availability.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getAvailability()));

        loadAllTherapists();
    }

    private void loadAllTherapists() {
        try {
            List<TherapistDto> all = therapistBO.getAllTherapists();
            ObservableList<TherapistDto> list = FXCollections.observableArrayList(all);
            therapistTable.setItems(list);
        } catch (Exception e) {
            showAlert("Failed to load data.",Alert.AlertType.INFORMATION);
            e.printStackTrace();
        }
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        TherapistDto selected = therapistTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a therapist from table to delete.", Alert.AlertType.WARNING);
            return;
        }
        if (selected != null) {
            try {
                therapistBO.deleteTherapist(selected.getTherapistId());
                showAlert("Therapist deleted!",Alert.AlertType.INFORMATION);
                clearFields();
                loadAllTherapists();
            } catch (Exception e) {
                showAlert("Delete failed.",Alert.AlertType.INFORMATION);
                e.printStackTrace();
            }
        }
    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        if (!validateTherapistInputs()) {
            showAlert("Please correct the highlighted fields.", Alert.AlertType.WARNING);
            return;
        }

        try {
            TherapistDto dto = new TherapistDto(
                    0,
                    tnameField.getText().trim(),
                    Integer.parseInt(tphoneField.getText().trim()),
                    specializationField.getText().trim(),
                    availabilityField.getText().trim()
            );
            therapistBO.saveTherapist(dto);
            showAlert("Therapist saved!", Alert.AlertType.INFORMATION);
            clearFields();
            loadAllTherapists();
        } catch (Exception e) {
            showAlert("Save failed.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }



    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        TherapistDto selected = therapistTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a therapist from table to update.", Alert.AlertType.WARNING);
            return;
        }

        if (!validateTherapistInputs()) {
            showAlert("Please correct the highlighted fields.", Alert.AlertType.WARNING);
            return;
        }

        try {
            selected.setName(tnameField.getText().trim());
            selected.setPhone(Integer.parseInt(tphoneField.getText().trim()));
            selected.setSpecialization(specializationField.getText().trim());
            selected.setAvailability(availabilityField.getText().trim());

            therapistBO.updateTherapist(selected);
            showAlert("Therapist updated!", Alert.AlertType.INFORMATION);
            clearFields();
            loadAllTherapists();
        } catch (Exception e) {
            showAlert("Update failed.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }


    private void clearFields() {
        tnameField.clear();
        tphoneField.clear();
        specializationField.clear();
        availabilityField.clear();
        nameError.setText("");
        phoneError.setText("");
        specializationError.setText("");
        availabilityError.setText("");
        therapistTable.getSelectionModel().clearSelection();
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Error" :
                type == Alert.AlertType.WARNING ? "Warning" : "Success");
        alert.showAndWait();
    }


    @FXML
    void OnTableClick(MouseEvent event) {
      TherapistDto selected = therapistTable.getSelectionModel().getSelectedItem();
      if (selected != null) {
          tnameField.setText(selected.getName());
          tphoneField.setText(String.valueOf(selected.getPhone()));
          specializationField.setText(selected.getSpecialization());
          availabilityField.setText(selected.getAvailability());
      }
    }

    @FXML
    void BtnClearOnAction(ActionEvent event) {
         clearFields();
    }

    private boolean validateTherapistInputs() {

        nameError.setText("");
        phoneError.setText("");
        specializationError.setText("");
        availabilityError.setText("");

        boolean isValid = true;

        String name = tnameField.getText().trim();
        String phone = tphoneField.getText().trim();
        String specialization = specializationField.getText().trim();
        String availability = availabilityField.getText().trim();


        if (name.isEmpty()) {
            nameError.setText("Name is required");
            isValid = false;
        }

        if ( !name.matches("^[A-Za-z. ]+$")&& !name.isEmpty()) {
            nameError.setText("letters and . only Ex :- A.G.Asan Indusara");
            isValid = false;
        }



        if (phone.isEmpty()) {
            phoneError.setText("phone is required");
            isValid = false;
        }


        if (!phone.matches("07\\d{8}") && !phone.isEmpty()) {
            phoneError.setText("start with 07,no spaces and 10 digits");
            isValid = false;
        }


        if (specialization.isEmpty()) {
            specializationError.setText("Specialization is required");
            isValid = false;
        }

        if ( !specialization.matches("^[A-Za-z ]+$") && !specialization.isEmpty()) {
            specializationError.setText("letters only");
            isValid = false;
        }


        if (availability.isEmpty()) {
            availabilityError.setText("Availability is required");
            isValid = false;
        }

        if (!availability.matches("^[A-Za-z ]+$") && !availability.isEmpty()) {
            availabilityError.setText("letters only");
            isValid = false;
        }

        return isValid;
    }

}