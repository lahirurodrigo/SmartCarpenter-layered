package lk.ijse.SmartCarpenter.bo.custom.impl;

import lk.ijse.SmartCarpenter.bo.custom.CustomerBO;
import lk.ijse.SmartCarpenter.dao.DAOFactory;
import lk.ijse.SmartCarpenter.dao.custom.CustomerDAO;
import lk.ijse.SmartCarpenter.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.SmartCarpenter.dto.CustomerDto;
import lk.ijse.SmartCarpenter.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public boolean save(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getTel()));
    }

    @Override
    public boolean update(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getTel()));
    }

    @Override
    public CustomerDto search(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(id);
        return new CustomerDto(customer.getId(), customer.getName(), customer.getAddress(), customer.getTel());
    }

    @Override
    public List<CustomerDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<CustomerDto> customerDTOS = new ArrayList<>();

        ArrayList<Customer> customers = (ArrayList<Customer>) customerDAO.getAll();

        for (Customer customer : customers) {
            customerDTOS.add(new CustomerDto(customer.getId(),customer.getName(),customer.getAddress(), customer.getTel()));
        }

        return customerDTOS;
    }
}
