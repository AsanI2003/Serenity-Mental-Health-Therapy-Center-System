package com.project.serenity.bo.custom.impl;

import com.project.serenity.bo.custom.PaymentBO;
import com.project.serenity.dao.DAOFactory;
import com.project.serenity.dao.custom.PaymentDAO;
import com.project.serenity.dto.PaymentDto;
import com.project.serenity.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public boolean updatePayment(PaymentDto dto) throws SQLException {
        Payment payment = new Payment();
        payment.setPaymentId(dto.getPaymentId());
        payment.setPaidAmount(dto.getPaidAmount());
        payment.setPendingAmount(dto.getPendingAmount());
        payment.setPaymentStatus(dto.getPaymentStatus());
        return paymentDAO.update(payment);
    }

    @Override
    public List<PaymentDto> getAllPayments() throws SQLException {
        List<Payment> payments = paymentDAO.getAll();
        List<PaymentDto> dtos = new ArrayList<>();

        for (Payment p : payments) {
            dtos.add(new PaymentDto(
                    p.getPaymentId(),
                    p.getPaidAmount(),
                    p.getPendingAmount(),
                    p.getPaymentStatus()
            ));
        }

        return dtos;
    }
}
