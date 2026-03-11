package com.project.serenity.controller;

import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvoiceController {

    @FXML
    private VBox rootVBox;

    @FXML
    private Label paymentIdLabel;

    @FXML
    private Label paidAmountLabel;

    @FXML
    private Label pendingAmountLabel;

    @FXML
    private Label paymentStatusLabel;

    @FXML
    private Label dateTimeLabel;

    public void setInvoiceData(int paymentId, double paidAmount, double pendingAmount, String paymentStatus) {
        paymentIdLabel.setText(String.valueOf(paymentId));
        paidAmountLabel.setText(String.format("%.2f", paidAmount));
        pendingAmountLabel.setText(String.format("%.2f", pendingAmount));
        paymentStatusLabel.setText(paymentStatus);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTimeLabel.setText(LocalDateTime.now().format(formatter));
    }

    @FXML
    private void printInvoice() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(rootVBox.getScene().getWindow())) {
            boolean success = job.printPage(rootVBox);
            if (success) {
                job.endJob();
            }
        }
    }
}
