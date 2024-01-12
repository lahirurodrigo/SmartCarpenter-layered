package lk.ijse.SmartCarpenter.bo.custom.impl;

import lk.ijse.SmartCarpenter.bo.custom.RawMaterialBO;
import lk.ijse.SmartCarpenter.dao.DAOFactory;
import lk.ijse.SmartCarpenter.dao.custom.RawMaterialDAO;
import lk.ijse.SmartCarpenter.dto.RawMaterialDto;
import lk.ijse.SmartCarpenter.entity.RawMaterial;

import java.sql.SQLException;
import java.util.List;

public class RawMaterialBOImpl implements RawMaterialBO {

    RawMaterialDAO rawMaterialDAO = (RawMaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RAW_MATERIAL);

    @Override
    public Object getTotal() throws SQLException {
        return rawMaterialDAO.getTotal();
    }

    @Override
    public boolean saveRawMaterial(RawMaterialDto dto) throws SQLException, ClassNotFoundException {
        return rawMaterialDAO.save(new RawMaterial(
                dto.getCode(),
                dto.getCategory(),
                dto.getUnitPrice(),
                dto.getQty()
        ));
    }

    @Override
    public boolean updateRawMaterial(RawMaterialDto dto) throws SQLException, ClassNotFoundException {
        return rawMaterialDAO.update(new RawMaterial(
                dto.getCode(),
                dto.getCategory(),
                dto.getUnitPrice(),
                dto.getQty()
        ));
    }

    @Override
    public List<String> getCodes() throws SQLException {
        return rawMaterialDAO.get();
    }

    @Override
    public RawMaterialDto searchRawMaterial(String code) throws SQLException, ClassNotFoundException {

        RawMaterial entity = rawMaterialDAO.search(code);

        return new RawMaterialDto(
                entity.getCode(),
                entity.getCategory(),
                entity.getUnitPrice(),
                entity.getQty()
        );
    }
}
