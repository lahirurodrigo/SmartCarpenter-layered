package lk.ijse.SmartCarpenter.dao.custom.impl;

import lk.ijse.SmartCarpenter.dao.SQLUtil;
import lk.ijse.SmartCarpenter.dao.custom.CustomerDAO;
import lk.ijse.SmartCarpenter.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean save(Customer entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO customer VALUES (?,?,?,?)",
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getTel()
        );
    }

    @Override
    public boolean update(Customer entity) throws SQLException {

        return SQLUtil.execute("UPDATE customer SET name= ?,address=?,email=? WHERE cus_id=?",
                entity.getName(),
                entity.getAddress(),
                entity.getTel(),
                entity.getId()
        );
    }

    @Override
    public List<Customer> getAll() throws SQLException {

        ArrayList<Customer> list = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer");

        while (resultSet.next()){
            list.add(new Customer(
                    resultSet.getString("cus_id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("email")
            ));
        }
        return list;
    }

    @Override
    public Customer search(String id) throws SQLException {

        ResultSet rs = SQLUtil.execute("SELECT * FROM customer WHERE cus_id = ?",id);

        Customer entity = null;

        if (rs.next()){
             entity = new Customer(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)
            );
        }
        return entity;
    }

    @Override
    public String getTotal() throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {

        return SQLUtil.execute("DELETE FROM customer WHERE cus_id = ?",id);

    }

    @Override
    public String generateNext() throws SQLException, ClassNotFoundException {
        return null;
    }


    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
