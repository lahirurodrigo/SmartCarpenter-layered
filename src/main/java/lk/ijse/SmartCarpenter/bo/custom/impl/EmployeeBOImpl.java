package lk.ijse.SmartCarpenter.bo.custom.impl;

import lk.ijse.SmartCarpenter.bo.BOFactory;
import lk.ijse.SmartCarpenter.bo.custom.EmployeeBO;
import lk.ijse.SmartCarpenter.dao.DAOFactory;
import lk.ijse.SmartCarpenter.dao.custom.EmployeeDAO;
import lk.ijse.SmartCarpenter.dao.custom.ManufacturingDeatilDAO;
import lk.ijse.SmartCarpenter.dao.custom.SalaryDAO;
import lk.ijse.SmartCarpenter.dto.CustomerDto;
import lk.ijse.SmartCarpenter.dto.EmployeeDto;
import lk.ijse.SmartCarpenter.dto.SalaryDto;
import lk.ijse.SmartCarpenter.entity.Customer;
import lk.ijse.SmartCarpenter.entity.Employee;
import lk.ijse.SmartCarpenter.entity.Salary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALARY);

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    ManufacturingDeatilDAO manufacturingDeatilDAO = (ManufacturingDeatilDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MANUFACTURING_DETAIL);

    @Override
    public Object getTotalSalary() throws SQLException {
        return salaryDAO.getTotal();
    }

    @Override
    public String getTotalEmployees() throws SQLException {
        return employeeDAO.getTotal();
    }

    @Override
    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(
                dto.getId(),
                dto.getName(),
                dto.getPosition(),
                dto.getGender(),
                dto.getAge()
                ));
    }

    @Override
    public boolean saveSalary(SalaryDto dto) throws SQLException, ClassNotFoundException {

        return salaryDAO.save(new Salary(
                dto.getSId(),
                dto.getEId(),
                dto.getMonth(),
                dto.getAmount()
        ));

    }

    @Override
    public EmployeeDto search(String id) throws SQLException, ClassNotFoundException {

        Employee employee = employeeDAO.search(id);

        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getPosition(),
                employee.getGender(),
                employee.getAge());
    }

    @Override
    public double getPaidSalaryAmount(String id, int month) throws SQLException {
        return salaryDAO.getAmount(id,month);
    }

    @Override
    public double getSalaryAmount(String id, int month) throws SQLException {
        return manufacturingDeatilDAO.getAmount(id,month);
    }

    @Override
    public List<EmployeeDto> getAll() throws SQLException, ClassNotFoundException {
        List<EmployeeDto> employeeDtos = new ArrayList<>();

        List<Employee> employees = employeeDAO.getAll();

        for (Employee employee : employees) {
            employeeDtos.add(new EmployeeDto(employee.getId(),employee.getName(),employee.getPosition(), employee.getGender(),employee.getAge()));
        }

        return employeeDtos;
    }

    @Override
    public String generateNextEId() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNext();
    }

    @Override
    public String generateNextSId() throws SQLException, ClassNotFoundException {
        return salaryDAO.generateNext();
    }
}
