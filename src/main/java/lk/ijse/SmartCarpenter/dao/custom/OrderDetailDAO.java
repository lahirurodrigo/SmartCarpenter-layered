package lk.ijse.SmartCarpenter.dao.custom;

import lk.ijse.SmartCarpenter.dao.CrudDAO;
import lk.ijse.SmartCarpenter.entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {

    double getTotalById(String id) throws SQLException;

    List<OrderDetail> getAll(String id) throws SQLException,ClassNotFoundException;
}
