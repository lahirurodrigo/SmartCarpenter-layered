package lk.ijse.SmartCarpenter.bo.custom.impl;

import lk.ijse.SmartCarpenter.bo.custom.PaymentBO;
import lk.ijse.SmartCarpenter.dao.DAOFactory;
import lk.ijse.SmartCarpenter.dao.custom.PaymentDAO;
import lk.ijse.SmartCarpenter.dto.PaymentDto;
import lk.ijse.SmartCarpenter.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public boolean savePayment(PaymentDto dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new Payment(
                dto.getId(),
                dto.getDate(),
                dto.getType(),
                dto.getAmount(),
                dto.getOId()
        ));
    }

    @Override
    public double getPaidAmount(String id) throws SQLException {
        return paymentDAO.getPaidAmount(id);
    }

    @Override
    public String generateNext() throws SQLException, ClassNotFoundException {
        return paymentDAO.generateNext();
    }

    @Override
    public List<PaymentDto> getAllPayments(String id) throws SQLException {

        List<PaymentDto> list = new ArrayList<>();

        List<Payment> payments = paymentDAO.getAll(id);

        for (Payment payment : payments) {
            list.add(new PaymentDto(
                    payment.getId(),
                    payment.getDate(),
                    payment.getType(),
                    payment.getAmount(),
                    payment.getOId()
            ));
        }

        return list;
    }
}
