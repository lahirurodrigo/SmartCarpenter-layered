package lk.ijse.SmartCarpenter.dao.custom.impl;

import lk.ijse.SmartCarpenter.dao.SQLUtil;
import lk.ijse.SmartCarpenter.dao.custom.CredentialDAO;
import lk.ijse.SmartCarpenter.dto.CredentialsDto;
import lk.ijse.SmartCarpenter.entity.Credentials;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CredentialDAOImpl implements CredentialDAO {

    @Override
    public boolean checkCredentials(Credentials entity) throws SQLException {

        ResultSet rs = SQLUtil.execute("SELECT * FROM credentials");

        while(rs.next()){
            System.out.println(rs.getString(1)+"   "+rs.getString(2));
            if (rs.getString(1).equals(entity.getUserName()) && rs.getString(2).equals(entity.getPassword())){

                return true;
            }
        }
        return false;
    }

    @Override
    public Credentials getCredentials() throws SQLException {

        ResultSet rs = SQLUtil.execute("SELECT * FROM credentials");

        Credentials entity =null;
        while(rs.next()) {
            entity = new Credentials(rs.getString(1), rs.getString(2));
        }

        return entity;
    }

    @Override
    public ArrayList<Credentials> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Credentials entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Credentials entity) throws SQLException, ClassNotFoundException {
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
    public Credentials search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getTotal() throws SQLException {
        return null;
    }
}
