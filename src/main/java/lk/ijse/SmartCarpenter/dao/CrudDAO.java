package lk.ijse.SmartCarpenter.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{

    List<T> getAll() throws SQLException, ClassNotFoundException;

    boolean save(T dTO) throws SQLException, ClassNotFoundException;

    boolean update(T dTO) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    String generateNext() throws SQLException, ClassNotFoundException;

    boolean exist(String id) throws SQLException, ClassNotFoundException;

    T search(String newValue) throws SQLException, ClassNotFoundException;

    public String getTotal() throws SQLException;
}
