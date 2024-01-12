package lk.ijse.SmartCarpenter.dao.custom.impl;

import lk.ijse.SmartCarpenter.dao.SQLUtil;
import lk.ijse.SmartCarpenter.dao.custom.EmployeeDAO;
import lk.ijse.SmartCarpenter.dto.EmployeeDto;
import lk.ijse.SmartCarpenter.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public String generateNext() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT e_id FROM employee WHERE e_id LIKE 'E00%' ORDER BY CAST(SUBSTRING(e_id, 4) AS UNSIGNED) DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    private String splitId(String currentOrderId) {
        if (currentOrderId == null || currentOrderId.isEmpty() || !currentOrderId.matches("^E\\d+$")) {
            return "E001";
        } else {
            String numericPart = currentOrderId.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "E00" + nextNumericPart;

        }
    }

    @Override
    public List<Employee> getAll() throws SQLException {

        ResultSet rs = SQLUtil.execute("SELECT * FROM employee");

        List<Employee> list = new ArrayList<>();

        while (rs.next()){
            list.add(new Employee(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5)
                    )
            );
        }
        return list;
    }

    @Override
    public boolean save(Employee entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO employee VALUES (?,?,?,?,?)",
                entity.getId(),
                entity.getPosition(),
                entity.getName(),
                entity.getGender(),
                entity.getAge()
        );


    }

    @Override
    public boolean update(Employee dTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Employee search(String id) throws SQLException {

        ResultSet rs = SQLUtil.execute("SELECT * FROM employee WHERE e_id = ?",id);

        Employee entity = null;

        if(rs.next()){
            entity = new Employee(
                    rs.getString(1),
                    rs.getString(3),
                    rs.getString(2),
                    rs.getString(4),
                    rs.getInt(5)
            );
        }
        return entity;
    }

    @Override
    public String getTotal() throws SQLException {

        int count = 0;

        ResultSet rs = SQLUtil.execute("SELECT * FROM employee");

        while (rs.next()){
            count++;
        }

        return String.valueOf(count);

    }
}
