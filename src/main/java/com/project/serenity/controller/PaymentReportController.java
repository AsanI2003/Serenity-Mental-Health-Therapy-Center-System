package com.project.serenity.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaymentReportController {

    @FXML
    private Label totalPaymentsLabel;

    @FXML
    private Label totalIncomeLabel;

    @FXML
    private AnchorPane rootPane;

    public void setReportData(int totalPayments, double totalIncome) {
        totalPaymentsLabel.setText("Total Payments: " + totalPayments);
        totalIncomeLabel.setText("Total Income: $" + totalIncome);
    }

    @FXML
    public void handlePrint() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(rootPane.getScene().getWindow())) {
            boolean success = job.printPage(rootPane);
            if (success) {
                job.endJob();
            }
        }
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
