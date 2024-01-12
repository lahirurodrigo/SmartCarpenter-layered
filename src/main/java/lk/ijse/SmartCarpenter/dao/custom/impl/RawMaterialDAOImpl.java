package lk.ijse.SmartCarpenter.dao.custom.impl;

import lk.ijse.SmartCarpenter.dao.SQLUtil;
import lk.ijse.SmartCarpenter.dao.custom.RawMaterialDAO;
import lk.ijse.SmartCarpenter.entity.RawMaterial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialDAOImpl implements RawMaterialDAO {
    @Override
    public List<RawMaterial> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(RawMaterial entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO rawMaterial VALUES (?,?,?,?)",
                entity.getCode(),
                entity.getCategory(),
                String.valueOf(entity.getUnitPrice()),
                String.valueOf(entity.getQty())
        );

    }

    @Override
    public List<String> get() throws SQLException {

        List<String> list = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT r_code FROM rawMaterial");

        while (rs.next()){
            list.add(rs.getString(1));
        }
        return  list;

    }

    @Override
    public RawMaterial search(String code) throws SQLException {

        RawMaterial entity = null;
        ResultSet rs = SQLUtil.execute("SELECT * FROM rawMaterial WHERE r_code = ?",code);

        while (rs.next()){
            entity = new RawMaterial(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4)
            );
        }

        return entity;

    }

    @Override
    public boolean update(RawMaterial entity) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT qty_on_hand FROM rawMaterial WHERE r_code=?",entity.getCode());

        int qty_on_hand;

        if (resultSet.next()){
            qty_on_hand = resultSet.getInt(1);
        }
        else {
            return false;
        }

        int quantity = qty_on_hand + entity.getQty();

        return SQLUtil.execute("UPDATE rawMaterial SET category = ?, unit_price = ?, qty_on_hand = ? WHERE r_code = ?",
                entity.getCategory(),
                String.valueOf(entity.getUnitPrice()),
                String.valueOf(quantity),
                entity.getCode()
        );
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
    public String getTotal() throws SQLException {

        double total = 0;
        ResultSet rs = SQLUtil.execute("SELECT unit_price,qty_on_hand FROM rawMaterial");
            while(rs.next()){
                total += rs.getInt(1)*rs.getDouble(2);
            }

        return String.valueOf(total);

    }
}
