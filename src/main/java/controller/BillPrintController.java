package controller;

import com.jfoenix.controls.JFXRadioButton;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.MonthBill;
import model.tm.MonthBillTm;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class BillPrintController {
    public BorderPane pane;
    public TableColumn colBalance;
    public TableColumn colSelectBtn;
    public TableColumn colYear;
    public TableColumn colMonth;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colPhone;
    public TableView<MonthBillTm> billTable;
    Connection conn= DBConnection.getConnection();

    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private ComboBox<String> monthComboBox;

    private List<MonthBill> billList=new ArrayList<>();

    @FXML
    public void initialize() {

        colBalance.setCellValueFactory(new PropertyValueFactory<>("Balance"));
        colSelectBtn.setCellValueFactory(new PropertyValueFactory<>("selectBtn"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Initialize the year ComboBox with some sample values
        for (int year = 2023; year <= Year.now().getValue(); year++) {
            yearComboBox.getItems().add(year);
        }
        // Initialize the month ComboBox with month names
        String[] months = new DateFormatSymbols().getMonths();
            monthComboBox.getItems().addAll(months);


        // You can add event handlers or additional initialization logic here
        yearComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newVal) -> {
            try {
                setBillTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        monthComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newVal) -> {
            try {
                setBillTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void setBillTable( ) throws SQLException {
        System.out.println("setBillTable Triggered");
        if (yearComboBox.getValue()!=null && ((String)monthComboBox.getValue()).length()>2){
            System.out.println("year"+yearComboBox.getValue()+"\n\n Month"+monthComboBox.getValue());
            System.out.println("setBill inside if");
            String sql="SELECT id FROM Customer";
            Statement stmt=conn.createStatement();
            ResultSet customerResultSet=stmt.executeQuery(sql);

            ObservableList<MonthBillTm> billTmList= FXCollections.observableArrayList();

            while(customerResultSet.next()){
                MonthBill bill=new MonthBill(customerResultSet.getString(1),yearComboBox.getValue(),Month.valueOf(monthComboBox.getValue().toUpperCase()).getValue());
                billList.add(bill);
                MonthBillTm tempMonthBillTm=new  MonthBillTm(bill);
                JFXRadioButton tempSelectBtn=new JFXRadioButton();
                tempSelectBtn.setOnAction(actionEvent -> {
                    System.out.println("selectedBillBTN pressed for :  "+bill.getName());
                });
                tempMonthBillTm.setSelectBtn(tempSelectBtn);
                billTmList.add(tempMonthBillTm);
            }

            billTable.setItems(billTmList);

        }


    }

    public void printSelectedOnPress(ActionEvent actionEvent) {
    }

    public void refreshOnPress(ActionEvent actionEvent) {
    }

    public void goBack(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"))));

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectAllToggle(ActionEvent actionEvent) {
    }
}
