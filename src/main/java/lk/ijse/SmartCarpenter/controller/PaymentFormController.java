package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.SmartCarpenter.bo.BOFactory;
import lk.ijse.SmartCarpenter.bo.custom.OrderBO;
import lk.ijse.SmartCarpenter.bo.custom.PaymentBO;
import lk.ijse.SmartCarpenter.dto.PaymentDto;
import lk.ijse.SmartCarpenter.dto.tm.PaymentTm;
import lk.ijse.SmartCarpenter.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.SmartCarpenter.dao.custom.impl.OrderDAOImpl;
import lk.ijse.SmartCarpenter.dao.custom.impl.PaymentDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PaymentFormController implements Initializable {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnMake;

    @FXML
    private JFXButton btnView;

    @FXML
    private JFXComboBox<String> cmbOIdDetails;

    @FXML
    private JFXComboBox<String> cmbOidPayments;

    @FXML
    private JFXComboBox<String> cmbType;


    @FXML
    private JFXComboBox<String> cmbOId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colPayId;

    @FXML
    private DatePicker dtpDate;

    @FXML
    private Label lblPaidDetails;

    @FXML
    private TableView<PaymentTm> tblPayments;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtBalanceDetails;

    @FXML
    private TextField txtPayId;

    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);

    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnMakeOnAction(ActionEvent event) {

        if(txtPayId.getText().isEmpty()){new Alert(Alert.AlertType.ERROR,"empty id").show(); return;}
        if(dtpDate.getValue() == null){new Alert(Alert.AlertType.ERROR,"empty date").show(); return;}
        if(txtAmount.getText().isEmpty()){new Alert(Alert.AlertType.ERROR,"empty value").show(); return;}
        if(cmbOId.getValue().isEmpty() || cmbOId.getValue() == null){
            new Alert(Alert.AlertType.ERROR,"select a order id").show(); return;
        }

        String id = txtPayId.getText();
        LocalDate date = dtpDate.getValue();
        String type =  cmbType.getValue();
        double amount = Double.parseDouble(txtAmount.getText());
        String oId = cmbOId.getValue();

        boolean isValid = validatePayment(date, String.valueOf(amount));

        if (!isValid){
            return;
        }

        PaymentDto dto = new PaymentDto(id,date,type,amount,oId);

        try {

            boolean isSaved = paymentBO.savePayment(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "payment saved successfully").show();
                return;
            }else{
                new Alert(Alert.AlertType.ERROR,"error").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean validatePayment(LocalDate date, String amount) {

       /* boolean matches = date.isBefore(LocalDate.now());
        if (!matches){
            new Alert(Alert.AlertType.ERROR,"Select a valid date").showAndWait();
            return false;
        }*/

        boolean matches2 = Pattern.matches("[0-9]{4,10}[.][0]|[0-9]{1,2}",amount);
        if (!matches2){
            new Alert(Alert.AlertType.ERROR,"Invalid unit price").showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    void btnViewOnAction(ActionEvent event) {

        String id = cmbOIdDetails.getValue();

        try {
            double orderTotal = orderBO.getOrderTotalById(id);
            double paidAmount = paymentBO.getPaidAmount(id);

            double balance = orderTotal - paidAmount;

            lblPaidDetails.setText(String.valueOf(paidAmount));
            txtBalanceDetails.setText(String.valueOf(balance));

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void cmbTypeOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setOrderIds();
        setTyes();
        dtpDate.setValue(LocalDate.now());
        setNextPayId();
        setCellValueFactory();
    }

    private void setNextPayId() {
        try {
            txtPayId.setText(paymentBO.generateNext());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTyes() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("cash");
        obList.add("card");

        cmbType.setItems(obList);

    }

    private void setOrderIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> list = orderBO.getAllOIds();

            for (String id : list){
                obList.add(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        cmbOId.setItems(obList);
        cmbOidPayments.setItems(obList);
        cmbOIdDetails.setItems(obList);
    }

    private void setCellValueFactory() {
        colPayId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    @FXML
    void cmbOidPaymentsOnAction(ActionEvent event) {
        String id = cmbOidPayments.getValue();
        loadAllPayments(id);
        System.out.println("11111111111");
    }

    private void loadAllPayments(String id) {

        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();

        try {
            List<PaymentDto> list = paymentBO.getAllPayments(id);

            for (PaymentDto dto : list){
                obList.add(new PaymentTm(
                        dto.getId(),
                        dto.getAmount()
                ));
            }
            System.out.println("2222222222");
            tblPayments.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}