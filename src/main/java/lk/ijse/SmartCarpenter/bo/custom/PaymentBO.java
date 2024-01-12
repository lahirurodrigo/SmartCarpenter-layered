package lk.ijse.SmartCarpenter.bo.custom;

import lk.ijse.SmartCarpenter.bo.SuperBO;
import lk.ijse.SmartCarpenter.dto.PaymentDto;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    boolean savePayment(PaymentDto dto) throws SQLException, ClassNotFoundException;

    double getPaidAmount(String id) throws SQLException;

    String generateNext() throws SQLException, ClassNotFoundException;

    List<PaymentDto> getAllPayments(String id) throws SQLException;
}
