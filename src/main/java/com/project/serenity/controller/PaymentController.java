package com.project.serenity.controller;

import com.jfoenix.controls.JFXTextField;
import com.project.serenity.bo.BOFactory;
import com.project.serenity.bo.custom.PaymentBO;
import com.project.serenity.dto.PaymentDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PaymentController {

    @FXML
    private Button generateinvoiceButton;

    @FXML
    private Button generatereportButton;

    @FXML
    private Button payclearButton;

    @FXML
    private TableColumn<PaymentDto, Integer> paymentID;

    @FXML
    private TableColumn<PaymentDto, Double> paidAmount;

    @FXML
    private TableColumn<PaymentDto, Double> pendingAmount;

    @FXML
    private TableColumn<PaymentDto, String> paymentStatus;

    @FXML
    private TableView<PaymentDto> paymentTable;

    @FXML
    private JFXTextField paidamountField;

    @FXML
    private JFXTextField pendingamountField;

    @FXML
    private JFXTextField paymentstatusField;

    @FXML
    private Label paidamountError;

    @FXML
    private Label pendingamountError;

    @FXML
    private Label paymentstatusError;

    @FXML
    private Button payupdateButton;

    private PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);

    private int selectedPaymentId = -1;

    @FXML
    void initialize() {
        setCellValueFactory();
        loadAllPayments();
    }

    private void setCellValueFactory() {
        paymentID.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getPaymentId()).asObject());
        paidAmount.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPaidAmount()).asObject());
        pendingAmount.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPendingAmount()).asObject());
        paymentStatus.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPaymentStatus()));
    }

    private void loadAllPayments() {
        try {
            List<PaymentDto> list = paymentBO.getAllPayments();
            ObservableList<PaymentDto> observableList = FXCollections.observableArrayList(list);
            paymentTable.setItems(observableList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error Loading Payments!", e.getMessage());
        }
    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        if (selectedPaymentId == -1) {
            showAlert(Alert.AlertType.WARNING, "No Payment Selected", "Please select a payment to update!");
            return;
        }

        if (validateFields()) {
            try {
                PaymentDto dto = new PaymentDto(
                        selectedPaymentId,
                        Double.parseDouble(paidamountField.getText()),
                        Double.parseDouble(pendingamountField.getText()),
                        paymentstatusField.getText()
                );

                boolean isUpdated = paymentBO.updatePayment(dto);

                if (isUpdated) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Payment updated successfully!");
                    clearFields();
                    loadAllPayments();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Update Failed", "Could not update the payment.");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Number", "Please enter valid numbers for amounts.");
            }
        }
    }

    @FXML
    void OnTableClick(MouseEvent event) {
        PaymentDto selected = paymentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selectedPaymentId = selected.getPaymentId();
            paidamountField.setText(String.valueOf(selected.getPaidAmount()));
            pendingamountField.setText(String.valueOf(selected.getPendingAmount()));
            paymentstatusField.setText(selected.getPaymentStatus());
        }
    }

    private boolean validateFields() {
        boolean isValid = true;

        if (paidamountField.getText().isEmpty()) {
            paidamountError.setText("Required");
            isValid = false;
        } else if (!paidamountField.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                paidamountError.setText("Enter a valid amount Ex :- 100 or 99.99");
                isValid = false;

        } else {
            paidamountError.setText("");
        }

        if (pendingamountField.getText().isEmpty()) {
            pendingamountError.setText("Required");
            isValid = false;
        } else if (!pendingamountField.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                pendingamountError.setText("Enter a valid amount Ex :- 100 or 99.99");
                isValid = false;

        } else {
            pendingamountError.setText("");
        }

        if (paymentstatusField.getText().isEmpty()) {
            paymentstatusError.setText("Required");
            isValid = false;
        }  else if (!paymentstatusField.getText().matches("^[A-Za-z ]+$")) {
                paymentstatusError.setText("letters Only");
                isValid = false;
        } else {
            paymentstatusError.setText("");
        }

        return isValid;
    }

    private void clearFields() {
        paidamountField.clear();
        pendingamountField.clear();
        paymentstatusField.clear();
        selectedPaymentId = -1;
        paidamountError.setText("");
        pendingamountError.setText("");
        paymentstatusError.setText("");
        paymentTable.getSelectionModel().clearSelection();

    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        new Alert(type, message, ButtonType.OK).show();
    }

    @FXML
    void BtnGenerateOnAction(ActionEvent event) {

        PaymentDto selectedPayment = (PaymentDto) paymentTable.getSelectionModel().getSelectedItem();

        if (selectedPayment == null) {

            new Alert(Alert.AlertType.WARNING, "Please select a payment record first!").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/serenity/view/invoice.fxml"));
            Parent root = loader.load();


            InvoiceController invoiceController = loader.getController();
            invoiceController.setInvoiceData(
                    selectedPayment.getPaymentId(),
                    selectedPayment.getPaidAmount(),
                    selectedPayment.getPendingAmount(),
                    selectedPayment.getPaymentStatus()
            );


            Stage stage = new Stage();
            stage.setTitle("Payment Invoice");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void BtnReportOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/serenity/view/paymentreport.fxml"));
            Parent root = loader.load();


            PaymentReportController reportController = loader.getController();

            int totalPayments = paymentTable.getItems().size();
            double totalIncome = 0;


            for (Object obj : paymentTable.getItems()) {
                PaymentDto payment = (PaymentDto) obj;
                totalIncome += payment.getPaidAmount();
            }


            reportController.setReportData(totalPayments, totalIncome);


            Stage stage = new Stage();
            stage.setTitle("Payment Summary Report");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void BtnClearOnAction(ActionEvent event) {
      clearFields();
    }
}
