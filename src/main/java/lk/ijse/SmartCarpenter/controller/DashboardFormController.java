package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.SmartCarpenter.bo.BOFactory;
import lk.ijse.SmartCarpenter.bo.custom.EmployeeBO;
import lk.ijse.SmartCarpenter.bo.custom.FurnitureBO;
import lk.ijse.SmartCarpenter.bo.custom.OrderBO;
import lk.ijse.SmartCarpenter.bo.custom.RawMaterialBO;
import lk.ijse.SmartCarpenter.dao.custom.impl.*;
import lk.ijse.SmartCarpenter.dto.OrderDto;
import lk.ijse.SmartCarpenter.dto.tm.DashBoardTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private PieChart pieChart;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnFurniture;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnMaterials;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnSalary;

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane rootDash;

    @FXML
    private AnchorPane rootVary;

    @FXML
    private Label lblEmp;

    @FXML
    private Label lblItem;

    @FXML
    private TableView<DashBoardTm> tblOrders;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    FurnitureBO furnitureBO = (FurnitureBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.FURNITURE);

    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    RawMaterialBO rawMaterialBO = (RawMaterialBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RAWMATERIAL);


    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        Parent rootNew = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));
        this.rootVary.getChildren().clear();
        this.rootVary.getChildren().add(rootNew);

    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        Parent rootNew = FXMLLoader.load(getClass().getResource("/view/employee_form.fxml"));
        this.rootVary.getChildren().clear();
        this.rootVary.getChildren().add(rootNew);
    }

    @FXML
    void btnFurnitureOnAction(ActionEvent event) throws IOException {
        Parent rootNew = FXMLLoader.load(getClass().getResource("/view/furniture_form.fxml"));
        this.rootVary.getChildren().clear();
        this.rootVary.getChildren().add(rootNew);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.centerOnScreen();
    }

    @FXML
    void btnMaterialsOnAction(ActionEvent event) throws IOException {
        Parent rootNew = FXMLLoader.load(getClass().getResource("/view/rawMaterial_form.fxml"));
        this.rootVary.getChildren().clear();
        this.rootVary.getChildren().add(rootNew);
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException {
        Parent rootNew = FXMLLoader.load(getClass().getResource("/view/order_form.fxml"));
        this.rootVary.getChildren().clear();
        this.rootVary.getChildren().add(rootNew);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        Parent rootNew = null;
        try {
            rootNew = FXMLLoader.load(getClass().getResource("/view/payment_form.fxml"));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

        this.rootVary.getChildren().clear();
        this.rootVary.getChildren().add(rootNew);
    }

    @FXML
    void btnSalaryOnAction(ActionEvent event) {

    }

    void initializePieChart() throws SQLException {

        //int order = (int) orderBO.getTotal();
        int order = 10;
        //int raw = (int) rawMaterialBO.getTotal();
        int raw = 20;
        //int salary = (int) employeeBO.getTotalSalary();
        int salary = 30;

        System.out.println("Order: "+order);
        System.out.println("Raw: "+raw);

        int or = order*100/(order+raw+salary);
        int r = raw*100/(order+raw+salary);
        int s = 100-(or+r);

        System.out.println("or: "+or );
        System.out.println("r: "+r );


        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Orders", or),
                new PieChart.Data("Salary", s),
                new PieChart.Data("Material cost", r)
        );

        pieChart.setData(pieChartData);

        for (PieChart.Data data : pieChart.getData()) {
            data.getNode().setOnMouseEntered(e -> {

                double value = data.getPieValue();

                System.out.println("Value: " + value);
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            initializePieChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setCellValueFactory();
        getAllOrderDetails();
        try {
            lblEmp.setText(employeeBO.getTotalEmployees());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            lblItem.setText(furnitureBO.getTotalItems());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAllOrderDetails() {

        ObservableList<DashBoardTm> obList = FXCollections.observableArrayList();

        try {
            List<OrderDto> list = orderBO.getAllOrders();

            for (OrderDto dto : list){
                DashBoardTm tm = new DashBoardTm(dto.getId(), String.valueOf(dto.getDueDate()));
                obList.add(tm);
            }

            tblOrders.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
}
