package lk.ijse.SmartCarpenter.dao.custom.impl;

import lk.ijse.SmartCarpenter.dao.SQLUtil;
import lk.ijse.SmartCarpenter.dao.custom.PaymentDAO;
import lk.ijse.SmartCarpenter.dto.PaymentDto;
import lk.ijse.SmartCarpenter.entity.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public List<Payment> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Payment entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO payment VALUES (?,?,?,?,?)",
                entity.getId(),
                Date.valueOf(entity.getDate()),
                entity.getType(),
                entity.getAmount(),
                entity.getOId()
        );
    }

    @Override
    public boolean update(Payment dTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public double getPaidAmount(String id) throws SQLException {

        double total = 0;
        ResultSet rs = SQLUtil.execute("SELECT * FROM payment WHERE o_id = ? ",id);

        while (rs.next()){
            total += rs.getDouble(4);
        }

        return total;

    }

    @Override
    public String generateNext() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT p_id FROM payment WHERE p_id LIKE 'P00%' ORDER BY CAST(SUBSTRING(p_id, 4) AS UNSIGNED) DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);

    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Payment search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getTotal() throws SQLException {
        return null;
    }

    private String splitId(String currentOrderId) {
        if (currentOrderId == null || currentOrderId.isEmpty() || !currentOrderId.matches("^P\\d+$")) {
            return "P001";
        } else {
            String numericPart = currentOrderId.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "P00" + nextNumericPart;

        }
    }

    @Override
    public List<Payment> getAll(String id) throws SQLException {

        List<Payment> itemList = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM payment WHERE o_id = ?",id);

        while (resultSet.next()) {
            itemList.add(new Payment(
                    resultSet.getString(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            ));
        }

        return itemList;
    }
}