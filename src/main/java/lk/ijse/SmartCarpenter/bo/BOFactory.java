package lk.ijse.SmartCarpenter.bo;

import lk.ijse.SmartCarpenter.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory() {

    }
    public static BOFactory getBoFactory() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        CUSTOMER, EMPLOYEE, FURNITURE, ORDER, PAYMENT, RAWMATERIAL, LOGIN
    }

    public SuperBO getBO(BOTypes boTypes) {
        switch (boTypes) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case FURNITURE:
                return new FurnitureBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case RAWMATERIAL:
                return new RawMaterialBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            default:
                return null;
        }
    }
}
