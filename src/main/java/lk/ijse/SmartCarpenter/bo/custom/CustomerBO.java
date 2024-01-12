package lk.ijse.SmartCarpenter.bo.custom;

import lk.ijse.SmartCarpenter.bo.SuperBO;
import lk.ijse.SmartCarpenter.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    boolean save(CustomerDto dto) throws SQLException, ClassNotFoundException;

    boolean update(CustomerDto dto) throws SQLException, ClassNotFoundException;

    CustomerDto search(String id) throws SQLException, ClassNotFoundException;

    List<CustomerDto> getAll() throws SQLException, ClassNotFoundException;
}
