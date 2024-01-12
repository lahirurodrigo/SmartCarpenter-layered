package lk.ijse.SmartCarpenter.dao.custom.impl;

import lk.ijse.SmartCarpenter.dao.SQLUtil;
import lk.ijse.SmartCarpenter.dao.custom.SalaryDAO;
import lk.ijse.SmartCarpenter.dto.SalaryDto;
import lk.ijse.SmartCarpenter.entity.Salary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SalaryDAOImpl implements SalaryDAO {

    @Override
    public String generateNext() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT s_id FROM salary WHERE s_id LIKE 'S00%' ORDER BY CAST(SUBSTRING(s_id, 4) AS UNSIGNED) DESC LIMIT 1");
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
    public Salary search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    private String splitId(String currentOrderId) {
        if (currentOrderId == null || currentOrderId.isEmpty() || !currentOrderId.matches("^S\\d+$")) {
            return "S001";
        } else {
            String numericPart = currentOrderId.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "S00" + nextNumericPart;

        }
    }

    @Override
    public List<Salary> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Salary entity) throws SQLException {

        return SQLUtil.execute("INSERT  INTO salary VALUES (?,?,?,?)",
                entity.getSId(),
                entity.getEId(),
                entity.getMonth(),
                entity.getAmount()
        );

    }

    @Override
    public boolean update(Salary dTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getTotal() throws SQLException {

        double total = 0;
            ResultSet rs = SQLUtil.execute("SELECT amount FROM salary");

            while(rs.next()){
                total += rs.getInt(1);
            }

            return String.valueOf(total);

    }

    @Override
    public double getAmount(String id, int month) throws SQLException {

        double paidAmount = 0;
        ResultSet rs = SQLUtil.execute("SELECT amount FROM salary WHERE e_id = ? AND month = ?",id,month);

        while (rs.next()){
            paidAmount += rs.getDouble(1);
        }

        return paidAmount;
    }
}
