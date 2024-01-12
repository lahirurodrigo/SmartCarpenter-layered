package lk.ijse.SmartCarpenter.bo.custom;

import lk.ijse.SmartCarpenter.bo.SuperBO;
import lk.ijse.SmartCarpenter.dto.CredentialsDto;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {
    CredentialsDto getCredentials() throws SQLException;

    boolean checkCredentials(CredentialsDto dto) throws SQLException;
}
