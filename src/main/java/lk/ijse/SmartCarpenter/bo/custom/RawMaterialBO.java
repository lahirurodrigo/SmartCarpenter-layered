package lk.ijse.SmartCarpenter.bo.custom;

import lk.ijse.SmartCarpenter.bo.SuperBO;
import lk.ijse.SmartCarpenter.dto.RawMaterialDto;

import java.sql.SQLException;
import java.util.List;

public interface RawMaterialBO extends SuperBO {
    Object getTotal() throws SQLException;

    boolean saveRawMaterial(RawMaterialDto dto) throws SQLException, ClassNotFoundException;

    boolean updateRawMaterial(RawMaterialDto dto) throws SQLException, ClassNotFoundException;

    List<String> getCodes() throws SQLException;

    RawMaterialDto searchRawMaterial(String code) throws SQLException, ClassNotFoundException;
}
