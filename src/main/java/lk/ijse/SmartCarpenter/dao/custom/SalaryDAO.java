package lk.ijse.SmartCarpenter.dao.custom;

import lk.ijse.SmartCarpenter.dao.CrudDAO;
import lk.ijse.SmartCarpenter.entity.Salary;

import java.sql.SQLException;

public interface SalaryDAO extends CrudDAO<Salary> {

    double getAmount(String id, int month) throws SQLException;
}
