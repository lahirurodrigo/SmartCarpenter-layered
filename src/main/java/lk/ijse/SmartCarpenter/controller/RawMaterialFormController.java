package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.SmartCarpenter.bo.BOFactory;
import lk.ijse.SmartCarpenter.bo.custom.RawMaterialBO;
import lk.ijse.SmartCarpenter.dto.RawMaterialDto;
import lk.ijse.SmartCarpenter.dao.custom.impl.RawMaterialDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class RawMaterialFormController {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnAddToStock;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXComboBox<String> cmbCodeManage;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableView<?> tblStock;

    @FXML
    private TextField txtCategory;

    @FXML
    private JFXTextField txtCategoryManage;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtQuantityManage;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private JFXTextField txtUnitPriceManage;

    RawMaterialBO rawMaterialBO = (RawMaterialBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RAWMATERIAL);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String category = txtCategory.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQuantity.getText());

        RawMaterialDto dto = new RawMaterialDto(code,category,unitPrice,qty);

        try {
            boolean isAdded = rawMaterialBO.saveRawMaterial(dto);

            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION, "Added successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"error").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnAddToStockOnAction(ActionEvent event) {
        String code = (String) cmbCodeManage.getValue();
        String category = txtCategoryManage.getText();
        double unitPrice = Double.parseDouble(txtUnitPriceManage.getText());
        int qty = Integer.parseInt(txtQuantityManage.getText());

        RawMaterialDto dto = new RawMaterialDto(code,category,unitPrice,qty);

        try {
            boolean isAdded = rawMaterialBO.updateRawMaterial(dto);

            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"error").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    public void initialize() {
        loadAllItemCodes();
    }

    private void loadAllItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> list = rawMaterialBO.getCodes();

            for (String code : list){
                obList.add(code);
            }

            cmbCodeManage.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void cmbCodeManageOnAction(ActionEvent event) {
        String code = cmbCodeManage.getValue();

        try {
            RawMaterialDto dto = rawMaterialBO.searchRawMaterial(code);

            txtCategoryManage.setText(dto.getCategory());
            txtUnitPriceManage.setText(String.valueOf(dto.getUnitPrice()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
