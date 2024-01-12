package lk.ijse.SmartCarpenter.dao.custom;

import lk.ijse.SmartCarpenter.dao.CrudDAO;
import lk.ijse.SmartCarpenter.dto.CredentialsDto;
import lk.ijse.SmartCarpenter.entity.Credentials;

import java.sql.SQLException;

public interface CredentialDAO extends CrudDAO<Credentials> {

    boolean checkCredentials(Credentials entity) throws SQLException;

    Credentials getCredentials() throws SQLException;
}
