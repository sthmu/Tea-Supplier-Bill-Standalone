package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Item;
import javafx.event.*;
import model.tm.ItemTm;

import javax.sql.StatementEvent;
import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;


public class ItemController implements Initializable {
    @FXML
    public JFXButton reloadBtn;
    @FXML
    Connection conn = DBConnection.getConnection();
    @FXML
    public TextField txtQty;
    @FXML
    public TextField txtUnitPrice;
    @FXML
    public TextField txtDescription;
    @FXML
    public TextField txtItemCode;
    @FXML
    public TableColumn colOption;
    @FXML
    public TableColumn colQty;
    @FXML
    public TableColumn colUnitPrice;
    @FXML
    public TableColumn colDescription;
    @FXML
    public TableColumn colItemCode;
    @FXML
    public TableView<ItemTm> tblItem;

    @Override
    public  void initialize(URL url, ResourceBundle resourceBundle) {

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colOption.setCellValueFactory(new PropertyValueFactory<Object,Button>("btn"));
        System.out.println("initialized");
        loadItemTable();


        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldvalue, newValue) -> {
            setText(newValue);
        });

    }

    private void setText(ItemTm newValue) {
        if(newValue!=null) {
            txtItemCode.setEditable(false);
            txtItemCode.setText(newValue.getItemCode());
            txtDescription.setText(newValue.getItemDescription());
            txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
            txtQty.setText(String.valueOf(newValue.getQty()));
        }
    }

    private void loadItemTable() {
        String sql = "SELECT * FROM item";
        ObservableList<ItemTm> itemList = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                Button btn = new Button("Delete");
                ItemTm itm = new ItemTm(
                        result.getString(1),
                        result.getString(2),
                        result.getDouble(3),
                        result.getInt(4),
                        btn
                );
                btn.setOnAction(actionEvent -> {
                    deleteItem(itm.getItemCode());
                });

                itemList.add(itm);
            }
            tblItem.setItems(itemList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void deleteItem(String itemCode) {
        String sql="DELETE FROM item where code='"+itemCode+"'";
        try {
            Statement stmt=conn.createStatement();
            int result=stmt.executeUpdate(sql);
            if(result>0){
                new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void addItemBtn(ActionEvent actionEvent) {
        Item item = new Item(txtItemCode.getText(), txtDescription.getText(), Double.parseDouble(txtUnitPrice.getText()), Integer.valueOf(txtQty.getText()));
        String sql = "INSERT INTO item (code,description,unitPrice,qtyOnHand) Values('" + item.getItemCode() + "','" + item.getItemDescription() + "'," + item.getUnitPrice() + "," + item.getQty() + ")";

        try {
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(sql);

            if (result > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Item Added to DataBase").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed To Add the ITem to the Database").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadItemTable();


    }

    public void updateItemBtn(ActionEvent actionEvent) {
        String sql="UPDATE item SET description='"+txtDescription.getText()+"',"+
                "unitPrice="+Double.parseDouble(txtUnitPrice.getText())+
                " , qtyOnHand="+Integer.parseInt(txtQty.getText())+
                " WHERE code='"+txtItemCode.getText()+"'";
        try {
            Statement stmt=conn.createStatement();
            int result=stmt.executeUpdate(sql);
            if(result>0){
                new Alert(Alert.AlertType.INFORMATION,"updated successfully").show();
                loadItemTable();
            }
        } catch (SQLException e) {
            System.out.println(sql);
            new Alert(Alert.AlertType.ERROR, e.getMessage()+"tis is the sql query:"+sql).show();
        }


    }


    public void refreshBtnOnAction(ActionEvent actionEvent) {
        loadItemTable();
        clearFields();

    }

    private void clearFields() {
        txtItemCode.setEditable(true);
        txtItemCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQty.clear();

    }

    public void goBack(ActionEvent actionEvent) {
        Stage stage= (Stage) tblItem.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("this is not working");
            throw new RuntimeException(e);
        }
    }
}
