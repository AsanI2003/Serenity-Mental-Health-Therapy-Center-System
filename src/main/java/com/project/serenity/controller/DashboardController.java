package com.project.serenity.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardController {

    @FXML
    private AnchorPane dashboardpane;

    @FXML
    private JFXButton logoutbutton;

    @FXML
    private JFXButton patientbutton;

    @FXML
    private JFXButton paymentbutton;

    @FXML
    private JFXButton programbutton;

    @FXML
    private AnchorPane maindashboardpane;

    @FXML
    private JFXButton sessionbutton;

    @FXML
    private JFXButton therapistbutton;

    //role eka add kra gna
    private String userRole;


    public void setUserRole(String role) {
        this.userRole = role;
        adjustUIForRole();
    }


    private void adjustUIForRole() {
        if (userRole.equals("Admin")) {

            patientbutton.setVisible(true);
            paymentbutton.setVisible(true);
            programbutton.setVisible(true);
            sessionbutton.setVisible(true);
            therapistbutton.setVisible(true);
        } else if (userRole.equals("Receptionist")) {

            patientbutton.setVisible(true);
            paymentbutton.setVisible(true);
            programbutton.setVisible(false);
            sessionbutton.setVisible(true);
            therapistbutton.setVisible(false);
        }

    }

    @FXML
    void onlogout(ActionEvent event) throws IOException {
        maindashboardpane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        maindashboardpane.getChildren().add(load);
    }

    @FXML
    void patientsclick(ActionEvent event) throws IOException {
        dashboardpane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/patient.fxml"));
        dashboardpane.getChildren().add(load);
    }

    @FXML
    void paymentsclick(ActionEvent event) throws IOException {
        dashboardpane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/payment.fxml"));
        dashboardpane.getChildren().add(load);
    }

    @FXML
    void programsclick(ActionEvent event) throws IOException {
        dashboardpane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/therapyprogram.fxml"));
        dashboardpane.getChildren().add(load);
    }

    @FXML
    void sessionsclick(ActionEvent event) throws IOException {
        dashboardpane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/therapysession.fxml"));
        dashboardpane.getChildren().add(load);
    }

    @FXML
    void therapistsclick(ActionEvent event) throws IOException {
        dashboardpane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/therapist.fxml"));
        dashboardpane.getChildren().add(load);
    }
}
