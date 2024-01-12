package lk.ijse.SmartCarpenter.bo.custom.impl;

import lk.ijse.SmartCarpenter.bo.custom.OrderBO;
import lk.ijse.SmartCarpenter.dao.DAOFactory;
import lk.ijse.SmartCarpenter.dao.custom.OrderDAO;
import lk.ijse.SmartCarpenter.dao.custom.OrderDetailDAO;
import lk.ijse.SmartCarpenter.dao.custom.impl.OrderDAOImpl;
import lk.ijse.SmartCarpenter.dto.CustomerDto;
import lk.ijse.SmartCarpenter.dto.OrderDetailDto;
import lk.ijse.SmartCarpenter.dto.OrderDto;
import lk.ijse.SmartCarpenter.entity.Customer;
import lk.ijse.SmartCarpenter.entity.Order;
import lk.ijse.SmartCarpenter.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public Object getTotal() throws SQLException {
        return orderDetailDAO.getTotal();
    }

    @Override
    public List<OrderDto> getAllOrders() throws SQLException, ClassNotFoundException {
        List<OrderDto> order = new ArrayList<>();

        List<Order> orders = orderDAO.getAll();

        for (Order dto : orders) {
            order.add(new OrderDto(dto.getId(),dto.getPlaceDate(),dto.getDueDate(), dto.getDuration(),dto.getCusId()));
        }

        return order;
    }

    @Override
    public List<String> getAllOIds() throws SQLException, ClassNotFoundException {
        List<String> order = new ArrayList<>();

        List<Order> orders = orderDAO.getAll();

        for (Order dto : orders) {
            order.add(dto.getId());
        }

        return order;
    }

    @Override
    public String generateNext() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNext();
    }

    @Override
    public boolean saveOrder(OrderDto dtoOrder) throws SQLException, ClassNotFoundException {
        return orderDAO.save(new Order(
                dtoOrder.getId(),
                dtoOrder.getPlaceDate(),
                dtoOrder.getDueDate(),
                dtoOrder.getDuration(),
                dtoOrder.getCusId()
        ));
    }

    @Override
    public boolean saveOrderDetail(OrderDetailDto orderDetailDto) throws SQLException, ClassNotFoundException {

        return orderDetailDAO.save(new OrderDetail(
                orderDetailDto.getOId(),
                orderDetailDto.getCode(),
                orderDetailDto.getQty(),
                orderDetailDto.getUniPrice()
        ));

    }

    @Override
    public List<OrderDetailDto> getAllOrderDetails(String id) throws SQLException, ClassNotFoundException {
        List<OrderDetailDto> order = new ArrayList<>();

        List<OrderDetail> oDetails = orderDetailDAO.getAll(id);

        for (OrderDetail dto : oDetails) {
            order.add(new OrderDetailDto(dto.getOId(),dto.getCode(),dto.getQty(), dto.getUniPrice()));
        }

        return order;
    }

    @Override
    public double getOrderTotalById(String id) throws SQLException {
        return orderDetailDAO.getTotalById(id);
    }
}
