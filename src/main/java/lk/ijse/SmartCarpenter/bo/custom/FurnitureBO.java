package lk.ijse.SmartCarpenter.bo.custom;

import lk.ijse.SmartCarpenter.bo.SuperBO;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;
import lk.ijse.SmartCarpenter.dto.ManufacturingDetailDto;

import java.sql.SQLException;
import java.util.List;

public interface FurnitureBO extends SuperBO {
    String getTotalItems() throws SQLException;

    boolean saveManufacturingDetail(ManufacturingDetailDto dtoMan) throws SQLException, ClassNotFoundException;

    boolean saveFurniture(FurnitureDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteFurniture(String code) throws SQLException, ClassNotFoundException;

    boolean updateFurniture(FurnitureDto dto) throws SQLException, ClassNotFoundException;

    FurnitureDto searchFurniture(String code) throws SQLException, ClassNotFoundException;

    String getCost(String code) throws SQLException;

    String generateNextCode() throws SQLException, ClassNotFoundException;

    List<FurnitureDto> getAll() throws SQLException, ClassNotFoundException;
}
