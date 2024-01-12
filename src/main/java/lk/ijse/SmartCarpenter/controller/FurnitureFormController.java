package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.SmartCarpenter.bo.BOFactory;
import lk.ijse.SmartCarpenter.bo.custom.EmployeeBO;
import lk.ijse.SmartCarpenter.bo.custom.FurnitureBO;
import lk.ijse.SmartCarpenter.dao.custom.impl.*;
import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.EmployeeDto;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;
import lk.ijse.SmartCarpenter.dto.ManufacturingDetailDto;
import lk.ijse.SmartCarpenter.dto.tm.FurnitureTm;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class FurnitureFormController implements Initializable {
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnClearUpdate;

    @FXML
    private JFXButton btnClearView;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnView;

    @FXML
    private JFXButton btndelete;

    @FXML
    private TableColumn<?, ?> ColQua;


    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableView<FurnitureTm> tblDetails;

    @FXML
    private JFXComboBox<String> cmbCodeUpdate;

    @FXML
    private JFXComboBox<String> cmbCodeView;

    @FXML
    private JFXComboBox<String> cmbEmpId;

    @FXML
    private JFXComboBox<String> cmbEmpIdUpdate;

    @FXML
    private Label lblEmployeeName;

    @FXML
    private TextField txtLabourCost;

    @FXML
    private JFXTextField txtLabourCostUpdate;

    @FXML
    private DatePicker dtpDate;

    @FXML
    private DatePicker dtpDateUpdate;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtUniPriceUpdate;

    @FXML
    private JFXTextField txtDescriptionUpdate;

    @FXML
    private JFXTextField txtDescriptionView;

    @FXML
    private TextField txtHeight;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtQuantityUpdate;

    @FXML
    private TextField txtQuantityView;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private JFXTextField txtUnitPriceView;

    @FXML
    private TextField txtWidth;

    @FXML
    private JFXTextField txtLabourCostView;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);;

    FurnitureBO furnitureBO = (FurnitureBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.FURNITURE);

    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException {

        if(txtDescription.getText().isEmpty()){new Alert(Alert.AlertType.ERROR,"insert description!").show(); return;}
        if(txtWidth.getText().isEmpty()){new Alert(Alert.AlertType.ERROR,"insert width!").show(); return;}
        if(txtHeight.getText().isEmpty()){new Alert(Alert.AlertType.ERROR,"insert height!").show(); return;}
        if(txtUnitPrice.getText().isEmpty()){new Alert(Alert.AlertType.ERROR,"insert unit price!").show(); return;}
        if(txtQuantity.getText().isEmpty()){new Alert(Alert.AlertType.ERROR,"insert Quantity to add!").show(); return;}
        if(cmbEmpId.getValue().isEmpty() || cmbEmpId.getValue() == null){new Alert(Alert.AlertType.ERROR,"select a employee id").show(); return;}
        if(dtpDate.getValue() == null){new Alert(Alert.AlertType.ERROR,"select a valid date").show(); return;}

        String code = txtCode.getText();
        String description = txtWidth.getText()+"*"+txtHeight.getText()+" "+txtDescription.getText();
        double unitPrice = Double.valueOf(txtUnitPrice.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        String empId = cmbEmpId.getValue();
        double labourCost = Double.parseDouble(txtLabourCost.getText());
        LocalDate date = dtpDate.getValue();

        System.out.println(description);

        boolean isValid = validateFurniture(code,txtWidth.getText(),txtHeight.getText(),txtDescription.getText(),txtUnitPrice.getText(),txtQuantity.getText(),empId,txtLabourCost.getText());

        if (!isValid){
            return;
        }

        FurnitureDto dto = new FurnitureDto(code,description,unitPrice,quantity);
        ManufacturingDetailDto dtoMan = new ManufacturingDetailDto(code,empId,labourCost,date,quantity);

        Connection connection = null;

        try {
            boolean flag = false;

            connection = DbConnection.getInstance().getConnection();

            connection.setAutoCommit(false);
            FurnitureDAOImpl furnitureDAO = new FurnitureDAOImpl();
            boolean isSaved = furnitureBO.saveFurniture(dto);

            if (isSaved){
                boolean isAdded = furnitureBO.saveManufacturingDetail(dtoMan);
                if (isAdded){
                    connection.commit();
                    flag = true;

                }else{
                    connection.rollback();
                    flag = false;
                }
            }else{
                connection.rollback();
                flag = false;
            }

            if (flag){
                new Alert(Alert.AlertType.CONFIRMATION, "furniture item saved!").show();
                clearFields();
                loadAllFurnitures();
                loadFurnitureCodes();
            }else{
                new Alert(Alert.AlertType.ERROR, "error").show();
            }

        } catch (SQLException e) {
            connection.rollback();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }

    }

    @FXML
    void cmbEmpOnAction(ActionEvent event) {
        String id = cmbEmpId.getValue();
        try {
            lblEmployeeName.setText(employeeBO.search(id).getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateFurniture(String code,String width,String height, String description, String unitPrice, String qty, String empId, String labourCost) {

        boolean matches = Pattern.matches("[F][0-9]{3,}",code);
        if (!matches) {
            new Alert(Alert.AlertType.ERROR,"Invalid furniture code").showAndWait();
            return false;
        }

        boolean matchesWidth = Pattern.matches("[0-9.]{1,2}",width);
        if (!matchesWidth) {
            new Alert(Alert.AlertType.ERROR,"Invalid width").showAndWait();
            return false;
        }

        boolean matchesHeight = Pattern.matches("[0-9.]{1,2}",width);
        if (!matchesHeight) {
            new Alert(Alert.AlertType.ERROR,"Invalid height").showAndWait();
            return false;
        }

        boolean matches1 = Pattern.matches("[A-Za-z\\s]{3,}",description);
        if (!matches1){
            new Alert(Alert.AlertType.ERROR,"Invalid description").showAndWait();
            return false;
        }

        boolean matches2 = Pattern.matches("[0-9]{4,10}[.][0]|[0-9]{4,10}",unitPrice);
        if (!matches2){
            new Alert(Alert.AlertType.ERROR,"Invalid unit price").showAndWait();
            return false;
        }

        boolean matches3 = Pattern.matches("[0-9]{1,3}",qty);
        if (!matches3){
            new Alert(Alert.AlertType.ERROR,"Invalid quantity").showAndWait();
            return false;
        }

        boolean matches4 = Pattern.matches("[E][0-9]{3,}",empId);
        if (!matches4) {
            new Alert(Alert.AlertType.ERROR,"Invalid employee id").showAndWait();
            return false;
        }

        boolean matches5 = Pattern.matches("[0-9]{4,10}[.][0]|[0-9]{4,10}",labourCost);
        if (!matches5){
            new Alert(Alert.AlertType.ERROR,"Invalid labour cost").showAndWait();
            return false;
        }

        return true;

    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtDescription.clear();
        txtHeight.clear();;
        txtWidth.clear();;
        txtCode.clear();
        txtQuantity.clear();
        txtUnitPrice.clear();
        txtDescriptionUpdate.clear();
        txtDescriptionView.clear();
        txtQuantityUpdate.clear();
        txtQuantityView.clear();
        txtUniPriceUpdate.clear();
        txtUnitPriceView.clear();
        txtLabourCost.clear();
        txtLabourCostUpdate.clear();
        txtLabourCostView.clear();
        /*cmbEmpId.setValue("");
        cmbCodeView.setValue("");
        cmbCodeUpdate.setValue("");
        cmbEmpIdUpdate.setValue("");*/

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String code = cmbCodeView.getValue();

        try {
            boolean isDeleted = furnitureBO.deleteFurniture(code);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item deleted!").show();
                loadAllFurnitures();
                loadFurnitureCodes();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Item not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String code = (String) cmbCodeUpdate.getValue();
        String desc = txtDescriptionUpdate.getText();
        double unitPrice = Double.parseDouble(txtUniPriceUpdate.getText());
        int qty = Integer.parseInt(txtQuantityUpdate.getText());
        String id = cmbEmpIdUpdate.getValue();
        double labourCost = Double.parseDouble(txtLabourCostUpdate.getText());
        LocalDate date = dtpDateUpdate.getValue();

        boolean isValid = validateFurniture(code,desc,txtUniPriceUpdate.getText(),txtQuantityUpdate.getText(),id,txtLabourCostUpdate.getText());


        if (!isValid){
            return;
        }

        FurnitureDto dto = new FurnitureDto(code,desc,unitPrice,qty);
        ManufacturingDetailDto dtoMan = new ManufacturingDetailDto(code,id,labourCost,date,qty);
        try {
            boolean isUpdated = furnitureBO.updateFurniture(dto);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "updated successfully").show();
                loadAllFurnitures();
                clearFields();
            }else{
                new Alert(Alert.AlertType.ERROR, "error").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private boolean validateFurniture(String code, String desc, String unitPrice, String qty, String id, String text2) {

        boolean matches = Pattern.matches("[F][0-9]{3,}",code);
        if (!matches) {
            new Alert(Alert.AlertType.ERROR,"Invalid furniture code").showAndWait();
            return false;
        }

        boolean matches2 = Pattern.matches("[0-9]{4,10}[.][0]|[0-9]{4,10}",unitPrice);
        if (!matches2){
            new Alert(Alert.AlertType.ERROR,"Invalid unit price").showAndWait();
            return false;
        }

        boolean matches3 = Pattern.matches("[0-9]{1,3}",qty);
        if (!matches3){
            new Alert(Alert.AlertType.ERROR,"Invalid quantity").showAndWait();
            return false;
        }

        return true;

    }

    @FXML
    void btnViewOnAction(ActionEvent event) {
        String code = (String) cmbCodeView.getValue();

        try {
            FurnitureDto dto = furnitureBO.searchFurniture(code);

            if(dto==null){
                new Alert(Alert.AlertType.ERROR,"No records found").showAndWait();
            }

            txtUnitPriceView.setText(String.valueOf(dto.getUnitPrice()));
            txtQuantityView.setText(String.valueOf(dto.getQtyOnHand()));
            txtDescriptionView.setText(dto.getDescription());
            txtLabourCostView.setText(furnitureBO.getCost(dto.getCode()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearViewOnAction(ActionEvent event) {
        clearFields();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadFurnitureCodes();
        setCellValueFactory();
        loadAllFurnitures();
        loadAllEmployeeIds();
        generateNextFurnitureCode();
        dtpDate.setValue(LocalDate.now());
        dtpDateUpdate.setValue(LocalDate.now());
    }

    private void generateNextFurnitureCode() {
        try {
            String code = furnitureBO.generateNextCode();
            txtCode.setText(code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllEmployeeIds() {
        ObservableList obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> dtoList = employeeBO.getAll();

            for (EmployeeDto dto: dtoList){
                obList.add(dto.getId());
            }
            cmbEmpId.setItems(obList);
            cmbEmpIdUpdate.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        ColQua.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }

    private void loadAllFurnitures() {

        ObservableList<FurnitureTm> obList = FXCollections.observableArrayList();

        try {
            List<FurnitureDto> list = furnitureBO.getAll();

            for (FurnitureDto dto : list){
                obList.add(new FurnitureTm(
                        dto.getCode(),
                        dto.getQtyOnHand()
                ));
            }
            tblDetails.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private void loadFurnitureCodes() {
        ObservableList<String> list  = FXCollections.observableArrayList();
        List<FurnitureDto> itemDtos = null;
        try {
            itemDtos = furnitureBO.getAll();
            for (FurnitureDto dto : itemDtos) {
                list.add(dto.getCode());
            }
            cmbCodeUpdate.setItems(list);
            cmbCodeView.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbCodeUpdateOnAction(ActionEvent event) {
        try {
            FurnitureDto dto = furnitureBO.searchFurniture(cmbCodeUpdate.getValue());
            txtDescriptionUpdate.setText(dto.getDescription());

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
