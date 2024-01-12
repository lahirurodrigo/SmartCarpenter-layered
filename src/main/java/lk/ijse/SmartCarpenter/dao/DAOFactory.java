package lk.ijse.SmartCarpenter.dao;

import lk.ijse.SmartCarpenter.dao.custom.impl.*;

public class DAOFactory {

    private  static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getDaoFactory() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CREDENTIAL, CUSTOMER, EMPLOYEE, FURNITURE, MANUFACTURING_DETAIL, ORDER, ORDER_DETAIL, PAYMENT, RAW_MATERIAL, SALARY
    }

    public SuperDAO getDAO(DAOTypes daoTypes) {
        switch (daoTypes) {
            case CREDENTIAL:
                return new CredentialDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case FURNITURE:
                return new FurnitureDAOImpl();
            case MANUFACTURING_DETAIL:
                return new ManufacturingDetailDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case RAW_MATERIAL:
                return new RawMaterialDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            default:
                return null;
        }
    }
}
