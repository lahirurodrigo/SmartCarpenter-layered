package lk.ijse.SmartCarpenter.dao;

import lk.ijse.SmartCarpenter.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {

    public static <T> T execute(String sql, Object...obj) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        for (int i = 0; i < obj.length; i++){
            pstm.setObject((i+1),obj[i]);
        }

        if (sql.startsWith("SELECT")){
            return (T) pstm.executeQuery();
        }else{
            return (T) (Boolean)(pstm.executeUpdate() > 0);
        }
    }

}
