package lk.ijse.SmartCarpenter.dao.custom.impl;

import lk.ijse.SmartCarpenter.dao.SQLUtil;
import lk.ijse.SmartCarpenter.dao.custom.ManufacturingDeatilDAO;
import lk.ijse.SmartCarpenter.dto.ManufacturingDetailDto;
import lk.ijse.SmartCarpenter.entity.ManufacturingDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.List;

public class ManufacturingDetailDAOImpl implements ManufacturingDeatilDAO {
    @Override
    public List<ManufacturingDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    public boolean save(ManufacturingDetail entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO manufacturingDetail VALUES (?,?,?,?,?)",
                entity.getCode(),
                entity.getEmpId(),
                String.valueOf(entity.getLabourCost()),
                String.valueOf(entity.getDate()),
                entity.getQty()
        );
    }

    @Override
    public boolean update(ManufacturingDetail dTO) throws SQLException, ClassNotFoundException {
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
    public ManufacturingDetail search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getTotal() throws SQLException {
        return null;
    }

    @Override
    public double getAmount(String id, int month) throws SQLException {

        Year currentYear = Year.now();
        int year = currentYear.getValue();
        double amount = 0;

        ResultSet rs = SQLUtil.execute("SELECT labour_cost,qty FROM manufacturingDetail WHERE e_id = ? AND YEAR(date) = ? AND MONTH(date) = ?",
                id,
                year,
                month
        );

        while (rs.next()){
            amount += rs.getDouble(1)*rs.getInt(2);
        }

        return amount;
    }

    @Override
    public String getCost(String code) throws SQLException {

        String labourCost = null;
        ResultSet rs = SQLUtil.execute("SELECT labour_cost FROM manufacturingDetail WHERE f_code = ? ORDER BY f_code DESC LIMIT 1",code);

        while (rs.next()){
            labourCost = rs.getString(1);
        }

        return labourCost;
    }
}
