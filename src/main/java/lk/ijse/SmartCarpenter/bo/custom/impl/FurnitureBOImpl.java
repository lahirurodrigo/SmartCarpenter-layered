package lk.ijse.SmartCarpenter.bo.custom.impl;

import lk.ijse.SmartCarpenter.bo.custom.FurnitureBO;
import lk.ijse.SmartCarpenter.dao.DAOFactory;
import lk.ijse.SmartCarpenter.dao.custom.FurnitureDAO;
import lk.ijse.SmartCarpenter.dao.custom.ManufacturingDeatilDAO;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;
import lk.ijse.SmartCarpenter.dto.ManufacturingDetailDto;
import lk.ijse.SmartCarpenter.entity.Furniture;
import lk.ijse.SmartCarpenter.entity.ManufacturingDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FurnitureBOImpl implements FurnitureBO {

    FurnitureDAO furnitureDAO = (FurnitureDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.FURNITURE);

    ManufacturingDeatilDAO manufacturingDeatilDAO = (ManufacturingDeatilDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MANUFACTURING_DETAIL);

    @Override
    public String getTotalItems() throws SQLException {
        return furnitureDAO.getTotal();
    }

    @Override
    public boolean saveManufacturingDetail(ManufacturingDetailDto dtoMan) throws SQLException, ClassNotFoundException {

        return manufacturingDeatilDAO.save(new ManufacturingDetail(
                dtoMan.getCode(),
                dtoMan.getEmpId(),
                dtoMan.getLabourCost(),
                dtoMan.getDate(),
                dtoMan.getQty()
        ));
    }

    @Override
    public boolean saveFurniture(FurnitureDto dto) throws SQLException, ClassNotFoundException {
        return furnitureDAO.save(new Furniture(
                dto.getCode(),
                dto.getDescription(),
                dto.getUnitPrice(),
                dto.getQtyOnHand()
        ));
    }

    @Override
    public boolean deleteFurniture(String code) throws SQLException, ClassNotFoundException {
        return furnitureDAO.delete(code);
    }

    @Override
    public boolean updateFurniture(FurnitureDto dto) throws SQLException, ClassNotFoundException {
        return furnitureDAO.update(new Furniture(
                dto.getCode(),
                dto.getDescription(),
                dto.getUnitPrice(),
                dto.getQtyOnHand()
        ));
    }

    @Override
    public FurnitureDto searchFurniture(String code) throws SQLException, ClassNotFoundException {

        Furniture dto = furnitureDAO.search(code);

        return new FurnitureDto(
                dto.getCode(),
                dto.getDescription(),
                dto.getUnitPrice(),
                dto.getQtyOnHand());
    }

    @Override
    public String getCost(String code) throws SQLException {
        return manufacturingDeatilDAO.getCost(code);
    }

    @Override
    public String generateNextCode() throws SQLException, ClassNotFoundException {
        return furnitureDAO.generateNext();
    }

    @Override
    public List<FurnitureDto> getAll() throws SQLException, ClassNotFoundException {

        List<FurnitureDto> dtos = new ArrayList<>();

        List<Furniture> dtoList = furnitureDAO.getAll();

        for (Furniture furniture : dtoList) {
            dtos.add(new FurnitureDto(
               furniture.getCode(),
               furniture.getDescription(),
               furniture.getUnitPrice(),
               furniture.getQtyOnHand()
            ));
        }
        return dtos;
    }
}
