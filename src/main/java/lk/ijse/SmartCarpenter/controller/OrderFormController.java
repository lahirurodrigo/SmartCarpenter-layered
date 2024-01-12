package lk.ijse.SmartCarpenter.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SmartCarpenter.bo.BOFactory;
import lk.ijse.SmartCarpenter.bo.custom.CustomerBO;
import lk.ijse.SmartCarpenter.bo.custom.EmployeeBO;
import lk.ijse.SmartCarpenter.bo.custom.FurnitureBO;
import lk.ijse.SmartCarpenter.bo.custom.OrderBO;
import lk.ijse.SmartCarpenter.dao.custom.impl.*;
import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.*;
import lk.ijse.SmartCarpenter.dto.tm.CartTm;
import lk.ijse.SmartCarpenter.dto.tm.FurnitureTm;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


public class OrderFormController  {

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnPrint;

    @FXML
    private JFXButton btnQR;


    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private JFXComboBox<String> cmbOid;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCodeInfo;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colQuanInfo;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private DatePicker dtpDue;

    @FXML
    private DatePicker dtpPlaced;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<CartTm> tblOrderCart;

    @FXML
    private TableView<FurnitureTm> tblStockInfo;

    @FXML
    private TextField txtQty;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    FurnitureBO furnitureBO = (FurnitureBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.FURNITURE);

    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() {

        setCellValueFactory();
        generateNextOrderId();
        loadCustomerIds();
        loadItemCodes();
        loadOrderIds();
        clearFields();
        loadAllFurnitures();

    }

    private void clearFields() {
        lblDescription.setText(null);
        lblCustomerName.setText(null);
        lblNetTotal.setText(null);
        lblUnitPrice.setText(null);
        lblQtyOnHand.setText(null);
        txtQty.setText(null);
        dtpDue.setValue(null);
    }

    private void loadOrderIds() {

        ObservableList<String> ob = FXCollections.observableArrayList();

        List<String> list = new ArrayList<>();

        try {
            list = orderBO.getAllOIds();

            for (String id : list){
                ob.add(id);
            }
        } catch (SQLException e) {
            System.out.println("");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        cmbOid.setItems(ob);
    }

    private void loadItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<FurnitureDto> furnitureDtos = furnitureBO.getAll();
            for (FurnitureDto dto : furnitureDtos) {
                obList.add(dto.getCode());
            }
            cmbItemCode.setItems(obList);
        } catch (SQLException e) {
            System.out.println("");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> idList = customerBO.getAll();

            for (CustomerDto dto : idList) {
                obList.add(dto.getId());
            }

            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            System.out.println("");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextOrderId() {

        String orderId = null;
        try {
            orderId = orderBO.generateNext();
            lblOrderId.setText(orderId);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
        colCodeInfo.setCellValueFactory(new PropertyValueFactory<>("code"));
        colQuanInfo.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
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
            tblStockInfo.setItems(obList);
        } catch (SQLException e) {
            System.out.println("");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

        if(!validateDates()){
            return;
        }

        if(cmbCustomerId.getValue() == null || cmbCustomerId.getValue().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"select a customer id").show(); return;
        }

        if(cmbItemCode.getValue() == null || cmbItemCode.getValue().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"select a item code").show(); return;
        }

        if (txtQty.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"enter the quantity to add").show(); return;
        }

        String code = cmbItemCode.getValue();
        String desc = lblDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty*unitPrice;
        Button btn = new Button("Remove");

        setRemoveBtnAction(btn);
        btn.setCursor(Cursor.HAND);

        CartTm cartTm = new CartTm(code,desc,qty,unitPrice,total,btn);

        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                if (colItemCode.getCellData(i).equals(code)) {
                    int col_qty = (int) colQty.getCellData(i);
                    qty += col_qty;
                    total = unitPrice * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(total);

                    calculateTotal();
                    tblOrderCart.refresh();
                    return;
                }
            }
        }

        obList.add(cartTm);

        tblOrderCart.setItems(obList);

        calculateTotal();
        txtQty.clear();

    }
    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblOrderCart.refresh();
                calculateTotal();
            }
        });
    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(total));
    }


    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {

        if (cmbCustomerId.getValue().isEmpty() || cmbCustomerId.getValue() == null){
            new Alert(Alert.AlertType.ERROR,"select a customer id").show(); return;
        }

        String id = lblOrderId.getText();
        LocalDate placedDate = dtpPlaced.getValue();
        LocalDate dueDate = dtpDue.getValue();
        String cusId = cmbCustomerId.getValue();

        if(!validateDates()){
            return;
        }

        /*if (cmbCustomerId.getValue().isEmpty() || cmbCustomerId.getValue() == null){
            new Alert(Alert.AlertType.ERROR,"select a customer id").show(); return;
        }*/

        Period period = Period.between(placedDate,dueDate);
        int duration = period.getDays();

        OrderDto dtoOrder = new OrderDto(id,placedDate,dueDate,duration,cusId);

        List<CartTm> cartTmList = new ArrayList<>();
        /*for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            CartTm cartTm = obList.get(i);

            cartTmList.add(cartTm);
        }*/

        for (CartTm tm : obList){
            cartTmList.add(tm);
        }

        if(cartTmList.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"cart is empty").show(); return;
        }

        //PlaceOrderDto dto = new PlaceOrderDto(dtoOrder,cartTmList);
        Connection connection = null ;
        try {
            boolean isPlaced = false;

            connection = DbConnection.getInstance().getConnection();

            connection.setAutoCommit(false);

            boolean isAdded = orderBO.saveOrder(dtoOrder);

            if (isAdded){

                for (CartTm tm : cartTmList) {
                    boolean isSaved = furnitureBO.updateFurniture(new FurnitureDto(tm.getCode(),tm.getDesc(), (double) tm.getUnitPrice(), (int) tm.getQty()));

                    if (isSaved){
                        boolean isUpdated = orderBO.saveOrderDetail(new OrderDetailDto(id,tm.getCode(),tm.getQty(),tm.getUnitPrice()));

                        if (isUpdated){
                            connection.commit();
                            connection.setAutoCommit(true);
                            isPlaced = true;
                        }else{
                            connection.rollback();
                            connection.setAutoCommit(true);
                            isPlaced =  false;
                        }
                    }else{
                        connection.rollback();
                        isPlaced = false;
                    }
                }
            }else{
                connection.rollback();
                isPlaced = false;
            }
            if (isPlaced){
                new Alert(Alert.AlertType.CONFIRMATION,"order placed successfully").show();
                //initialize();
                clearFields();
            }else{
                new Alert(Alert.AlertType.ERROR,"error").show();
            }
        } catch (SQLException e) {
            connection.rollback();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }


    }

    private boolean validateDates() {

        if (dtpDue.getValue() == null || dtpPlaced.getValue() == null){
            new Alert(Alert.AlertType.ERROR,"select a date").show();
            return false;
        }

        if (dtpDue.getValue().isBefore(dtpPlaced.getValue())){
            new Alert(Alert.AlertType.ERROR,"due date cannot be before place date").show();
            return false;
        }

        if (dtpPlaced.getValue().isBefore(LocalDate.now()) || dtpDue.getValue().isBefore(LocalDate.now())){
            new Alert(Alert.AlertType.ERROR,"select a valid date").show();
            return false;
        }
        return true;
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String id =  cmbCustomerId.getValue();

        try {
            CustomerDto customerDto =customerBO.search(id);
            lblCustomerName.setText(customerDto.getName());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String code =  cmbItemCode.getValue();

        txtQty.requestFocus();

        try {
            FurnitureDto dto = furnitureBO.searchFurniture(code);
            lblDescription.setText(dto.getDescription());
            lblUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
            lblQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }

    @FXML
    void btnPrintOnAction(ActionEvent event) {

    }

    @FXML
    void btnQROnAction(ActionEvent event) {

        String id = cmbOid.getValue();

        if (cmbOid.getValue() == null || cmbOid.getValue().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"choose an id first").showAndWait();
            return;
        }

        List<OrderDetailDto> list = null;

        try {
             list = orderBO.getAllOrderDetails(id);

            try {

                for(OrderDetailDto dto : list){

                    LocalDate date = LocalDate.now();
                    String itemCode = dto.getCode();
                    double uniPrice = dto.getUniPrice();

                    for (int i = 0; i < dto.getQty(); i++) {

                        System.out.println(i);

                        String data = "MH Furniture\nItem code: "+itemCode+"  Date of sold: "+date+"  Unit Price: "+uniPrice;
                        String filePath = "/home/lahiru/Documents/QR Codes/"+dto.getCode()+" "+i+" "+date;
                        int width = 300;
                        int height = 300;
                        generateQRCode(data, filePath, width, height);
                    }

                    new Alert(Alert.AlertType.INFORMATION,"QR Codes Generated").showAndWait();
                }
            } catch (WriterException | IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void generateQRCode(String data, String filePath, int width, int height) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        System.out.println("QR code generated successfully at: " + filePath);
    }

}
