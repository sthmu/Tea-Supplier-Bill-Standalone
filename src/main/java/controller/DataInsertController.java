package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DataInsertController implements Initializable {
    Connection conn= DBConnection.getConnection();
    public GridPane pane;
    public JFXTextField txtFieldContainCost;
    public JFXTextField txtFertilizer;
    public JFXComboBox customerCombo;
    public JFXTextField txtTeaPacket;
    public JFXTextField txtFieldKg;
    public DatePicker datePicker;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            customerCombo.setItems(getCustomerNameList());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//detect changes in the comboBox and the datePicker and then setting the info page if the changes are valid
        customerCombo.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newVal) -> {
            setInfoPage();
        });
        datePicker.valueProperty().addListener((observableValue, localDate, newDate) -> {
            setInfoPage();
        });


    }

    private void setInfoPage() {
        System.out.println("setInfoPage");
    }

    //getting the customer name list to the combobox to show
    private ObservableList getCustomerNameList() throws SQLException {
        String sql="SELECT * FROM CUSTOMER";
        Statement stmt=conn.createStatement();
        ResultSet result=stmt.executeQuery(sql);
        ObservableList<String> custNameList= FXCollections.observableArrayList();
        while(result.next()){
            System.out.println(result.getString(2));
            custNameList.add(result.getString(2));
        }
        return custNameList;
    }


    public void goBack(ActionEvent actionEvent) {
        Stage stage= (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"))));

            stage.show();
        } catch (IOException e) {
            System.out.println("this is not working");
            throw new RuntimeException(e);
        }
    }

    public void addSupplier(MouseEvent mouseEvent) {
    }


//after validation update if the recExists if not insert a new rec

    public void updateOnButton(ActionEvent actionEvent) throws SQLException {
        String date= String.valueOf(datePicker.getValue());
        String id=getCustId((String)(customerCombo.getValue()));
        if(checkIfRecExists(date,id)){
            updateRec(id,date);
        }
        else{
            insertRec(id,date);
        }

    }
    //ACTUAL INSERT METHOD THIS REALLY INSERT THE TABLE NOICE!!!!
    private void insertRec(String id, String date) {
    }

    //ACTUAL UPDATE TABLE METHOD THIS REALLY UPDATES THE TABLE NOICE!!!!
    private void updateRec(String id, String date) {

    }

    //get the customer id from the selected custmer name from the combo box
    private String getCustId(String custComboValue) throws SQLException {
        String sql="Select id from customer where name='"+custComboValue+"'";

        Statement stmt=conn.createStatement();
        ResultSet result=stmt.executeQuery(sql);
        result.next();
        return result.getString(1);
    }
//Record need to exist in order to update if not then its just gonna insert a new record to the table
    private boolean checkIfRecExists(String date,String id){
         String sql="SELECT * FROM records where date='"+date+"' AND id="+id;

        try {
            Statement stmt=conn.createStatement();
            ResultSet result=stmt.executeQuery(sql);
            return result.getFetchSize()>=1?true:false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
