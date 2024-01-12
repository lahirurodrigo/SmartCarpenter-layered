package lk.ijse.SmartCarpenter.bo.custom;

import lk.ijse.SmartCarpenter.bo.SuperBO;
import lk.ijse.SmartCarpenter.dto.EmployeeDto;
import lk.ijse.SmartCarpenter.dto.SalaryDto;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    Object getTotalSalary() throws SQLException;

    String getTotalEmployees() throws SQLException;

    boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;

    boolean saveSalary(SalaryDto dto) throws SQLException, ClassNotFoundException;

    EmployeeDto search(String id) throws SQLException, ClassNotFoundException;

    double getPaidSalaryAmount(String id, int month) throws SQLException;

    double getSalaryAmount(String id, int month) throws SQLException;

    List<EmployeeDto> getAll() throws SQLException, ClassNotFoundException;

    String generateNextEId() throws SQLException, ClassNotFoundException;

    String generateNextSId() throws SQLException, ClassNotFoundException;
}
