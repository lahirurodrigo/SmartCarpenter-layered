package lk.ijse.SmartCarpenter.dao.custom;

import lk.ijse.SmartCarpenter.dao.CrudDAO;
import lk.ijse.SmartCarpenter.entity.Payment;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {

    double getPaidAmount(String id) throws SQLException;

    List<Payment> getAll(String id) throws SQLException;
}
