package lk.ijse.SmartCarpenter.bo.custom.impl;

import lk.ijse.SmartCarpenter.bo.custom.LoginBO;
import lk.ijse.SmartCarpenter.dao.DAOFactory;
import lk.ijse.SmartCarpenter.dao.custom.CredentialDAO;
import lk.ijse.SmartCarpenter.dto.CredentialsDto;
import lk.ijse.SmartCarpenter.entity.Credentials;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {

    CredentialDAO credentialDAO = (CredentialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CREDENTIAL);

    @Override
    public CredentialsDto getCredentials() throws SQLException {

        Credentials credentials = credentialDAO.getCredentials();
        return new CredentialsDto(credentials.getUserName(),credentials.getPassword()) ;
    }

    @Override
    public boolean checkCredentials(CredentialsDto dto) throws SQLException {
        return credentialDAO.checkCredentials(new Credentials(dto.getUserName(), dto.getPassword()));
    }
}
