package com.project.serenity.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.project.serenity.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

import  com.project.serenity.utill.FactoryConfiguration;

public class LoginController {

    @FXML
    private Button loginbutton;

    @FXML
    private AnchorPane loginpane;

    @FXML
    private JFXPasswordField passwordfield;

    @FXML
    private JFXTextField userfield;

    @FXML
    void onClick(ActionEvent event) throws IOException {
        String username = userfield.getText();
        String password = passwordfield.getText();

        if (username.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Username and Password cannot be empty!").show();
            return;
        }

        try (Session session = FactoryConfiguration.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();


            String hql = "FROM User WHERE username = :username";
            Query query = session.createQuery(hql);
            query.setParameter("username", username);


            List result = query.list();
            transaction.commit();

            if (!result.isEmpty()) {
                User user = (User) result.get(0);
                String storedHash = user.getPassword();
                String role = user.getRole();


                BCrypt.Result resultVerification = BCrypt.verifyer().verify(password.toCharArray(), storedHash);

                if (resultVerification.verified) {

                    loginpane.getChildren().clear();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
                    AnchorPane load = loader.load();


                    DashboardController dashboardController = loader.getController();
                    dashboardController.setUserRole(role);

                    loginpane.getChildren().add(load);
                    System.out.println("Login successful as " + role);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid Password!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "User not found!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error!").show();
        }
    }
}
