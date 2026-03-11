package com.project.serenity.bo.custom;

import com.project.serenity.bo.SuperBO;
import com.project.serenity.dto.PaymentDto;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    boolean updatePayment(PaymentDto dto) throws SQLException;

    List<PaymentDto> getAllPayments() throws SQLException;
}
