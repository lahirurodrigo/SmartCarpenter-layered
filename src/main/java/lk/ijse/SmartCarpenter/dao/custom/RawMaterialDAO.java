package lk.ijse.SmartCarpenter.dao.custom;

import lk.ijse.SmartCarpenter.dao.CrudDAO;
import lk.ijse.SmartCarpenter.entity.RawMaterial;

import java.sql.SQLException;
import java.util.List;

public interface RawMaterialDAO extends CrudDAO<RawMaterial> {

    List<String> get() throws SQLException;
}
