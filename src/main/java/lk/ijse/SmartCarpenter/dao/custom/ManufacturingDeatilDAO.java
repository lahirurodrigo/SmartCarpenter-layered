package lk.ijse.SmartCarpenter.dao.custom;

import lk.ijse.SmartCarpenter.dao.CrudDAO;
import lk.ijse.SmartCarpenter.entity.ManufacturingDetail;

import java.sql.SQLException;

public interface ManufacturingDeatilDAO extends CrudDAO<ManufacturingDetail> {

    double getAmount(String id, int month) throws SQLException;

    String getCost(String code) throws SQLException;
}
