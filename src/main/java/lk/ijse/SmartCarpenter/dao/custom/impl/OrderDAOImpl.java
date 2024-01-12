package lk.ijse.SmartCarpenter.dao.custom.impl;

import lk.ijse.SmartCarpenter.dao.SQLUtil;
import lk.ijse.SmartCarpenter.dao.custom.OrderDAO;
import lk.ijse.SmartCarpenter.dto.OrderDto;
import lk.ijse.SmartCarpenter.dto.tm.DashBoardTm;
import lk.ijse.SmartCarpenter.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean save(Order entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO orders VALUES (?,?,?,?,?)",
                entity.getId(),
                Date.valueOf(entity.getPlaceDate()),
                Date.valueOf(entity.getDueDate()),
                entity.getDuration(),
                entity.getCusId()
        );

    }

    @Override
    public boolean update(Order dTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNext() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT o_id FROM orders WHERE o_id LIKE 'O00%' ORDER BY CAST(SUBSTRING(o_id, 4) AS UNSIGNED) DESC LIMIT 1");
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
    public Order search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getTotal() throws SQLException {
        return null;
    }

    private String splitId(String currentOrderId) {
        if (currentOrderId == null || currentOrderId.isEmpty() || !currentOrderId.matches("^O\\d+$")) {
            return "O001";
        } else {
            String numericPart = currentOrderId.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "O00" + nextNumericPart;

        }
    }

    @Override
    public List<Order> getAll() throws SQLException {

        List<Order> orderList = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT * FROM orders");

        while (rs.next()){
            orderList.add(new Order(
                    rs.getString(1),
                    rs.getDate(2).toLocalDate(),
                    rs.getDate(3).toLocalDate(),
                    rs.getInt(4),
                    rs.getString(5)
            ));
        }
        return  orderList;
    }

    /*public static List<DashBoardTm> getAllOrders() throws SQLException {

        List<DashBoardTm> list = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT o_id,due_date FROM orders WHERE duration > 0 ORDER BY due_date");

        while (rs.next()){
            list.add(new DashBoardTm(
                    rs.getString(1),
                    rs.getString(2)
            ));
        }

        return list;

    }*/
}
