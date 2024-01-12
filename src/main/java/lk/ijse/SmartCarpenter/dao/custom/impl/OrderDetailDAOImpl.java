package lk.ijse.SmartCarpenter.dao.custom.impl;

import lk.ijse.SmartCarpenter.dao.SQLUtil;
import lk.ijse.SmartCarpenter.dao.custom.OrderDetailDAO;
import lk.ijse.SmartCarpenter.dto.OrderDetailDto;
import lk.ijse.SmartCarpenter.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public List<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDetail entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO orderDetails VALUES (?,?,?,?) ",
                entity.getOId(),
                entity.getCode(),
                entity.getQty(),
                entity.getUniPrice()
        );

    }

    public boolean update(OrderDetail entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNext() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public double getTotalById(String id) throws SQLException {

        double total = 0;
        ResultSet rs = SQLUtil.execute("SELECT * FROM orderDetails WHERE o_id = ?",id);

        while (rs.next()){
            total+= rs.getDouble(4)*rs.getInt(3);
        }
        return total;
    }

    @Override
    public String getTotal() throws SQLException {

        double total = 0;
        ResultSet rs = SQLUtil.execute("SELECT qty,unit_price FROM orderDetails");

        while(rs.next()) {
            total += rs.getInt(1) * rs.getDouble(2);
        }
        return String.valueOf(total);
    }

    @Override
    public List<OrderDetail> getAll(String id) throws SQLException,ClassNotFoundException{

        ResultSet rs = SQLUtil.execute("SELECT * FROM orderDetails WHERE o_id = ?",id);

        List<OrderDetail> list = new ArrayList<>();

        while(rs.next()){
            OrderDetail entity = new OrderDetail(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getDouble(4)
                    );

            list.add(entity);

        }
        return  list;
    }
}