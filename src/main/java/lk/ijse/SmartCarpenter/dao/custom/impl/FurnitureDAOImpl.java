package lk.ijse.SmartCarpenter.dao.custom.impl;

import lk.ijse.SmartCarpenter.dao.SQLUtil;
import lk.ijse.SmartCarpenter.dao.custom.FurnitureDAO;
import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;
import lk.ijse.SmartCarpenter.entity.Furniture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FurnitureDAOImpl implements FurnitureDAO {

    @Override
    public boolean save(Furniture entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO furniture VALUES (?,?,?,?)",
                entity.getCode(),
                entity.getDescription(),
                String.valueOf(entity.getUnitPrice()),
                String.valueOf(entity.getQtyOnHand())
                );
    }

    public boolean delete(String code) throws SQLException {

        return  SQLUtil.execute("DELETE FROM furniture WHERE f_code=?",code);

    }

    @Override
    public boolean update(Furniture entity) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT qty_on_hand FROM furniture WHERE f_code=?",entity.getCode());

        int qty_on_hand;

        if (resultSet.next()){
            qty_on_hand = resultSet.getInt(1);
        }
        else {
            return false;
        }

        int quantity = qty_on_hand + entity.getQtyOnHand();

        return SQLUtil.execute("UPDATE furniture SET description = ?, unit_price = ?, qty_on_hand = ? WHERE f_code = ?",
                entity.getDescription(),
                String.valueOf(entity.getUnitPrice()),
                String.valueOf(quantity),
                entity.getCode()
                );

    }

    @Override
    public List<Furniture> getAll() throws SQLException {
       /* Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM furniture";
        PreparedStatement pstm = connection.prepareStatement(sql);*/

        List<Furniture> itemList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM furniture");
        while (resultSet.next()) {
            itemList.add(new Furniture(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            ));
        }

        return itemList;
    }

    @Override
    public Furniture search(String code) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM furniture WHERE f_code = ?",code);

        if (resultSet.next()){
            return new Furniture(resultSet.getString(1),
            resultSet.getString(2),
            resultSet.getDouble(3),
            resultSet.getInt(4));
        }
        return null;
    }

    @Override
    public String generateNext() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT f_code FROM furniture WHERE f_code LIKE 'F00%' ORDER BY CAST(SUBSTRING(f_code, 4) AS UNSIGNED) DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    private String splitId(String currentOrderId) {
        if (currentOrderId == null || currentOrderId.isEmpty() || !currentOrderId.matches("^F\\d+$")) {
            return "F001";
        } else {
            String numericPart = currentOrderId.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "F00" + nextNumericPart;

        }
    }

    @Override
    public String getTotal() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM furniture";
        PreparedStatement pstm = connection.prepareStatement(sql);

        int count = 0;

        ResultSet rs = pstm.executeQuery();

        while (rs.next()){
            count++;
        }

        return String.valueOf(count);
    }

}
