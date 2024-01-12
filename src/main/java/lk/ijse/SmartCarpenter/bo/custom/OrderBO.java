package lk.ijse.SmartCarpenter.bo.custom;

import lk.ijse.SmartCarpenter.bo.SuperBO;
import lk.ijse.SmartCarpenter.dto.OrderDetailDto;
import lk.ijse.SmartCarpenter.dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {
    Object getTotal() throws SQLException;

    List<OrderDto> getAllOrders() throws SQLException, ClassNotFoundException;

    List<String> getAllOIds() throws SQLException, ClassNotFoundException;

    String generateNext() throws SQLException, ClassNotFoundException;

    boolean saveOrder(OrderDto dtoOrder) throws SQLException, ClassNotFoundException;

    boolean saveOrderDetail(OrderDetailDto orderDetailDto) throws SQLException, ClassNotFoundException;

    List<OrderDetailDto> getAllOrderDetails(String id) throws SQLException, ClassNotFoundException;

    double getOrderTotalById(String id) throws SQLException;
}
