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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.MonthBill;
import model.tm.Kgtm;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DataInsertController implements Initializable {
    public JFXTextField txtOther;
    public JFXTextField txtAdvance;
    public Label lblTransport;
    public Label lblTeaPacket;
    public Label lblFertilizer;
    public Label lblContainers;
    public Label lblAdvance;
    public Label lblBalance;
    public Label lblWholeSub;
    public Label lbltTeaSum;
    public Label lblOtherSum;
    public Label lblWholeSum;
    public TableView<Kgtm> tblKg;
    public TableColumn colDate;
    public TableColumn colKg;
    public Label lblOtherSub;
    public AnchorPane billPane;
    public ColumnConstraints pane1C;
    Connection conn = DBConnection.getConnection();
    public GridPane pane;
    public JFXTextField txtFieldContainCost;
    public JFXTextField txtFertilizer;
    public JFXComboBox<String> customerCombo;
    public JFXTextField txtTeaPacket;
    public JFXTextField txtFieldKg;
    public DatePicker datePicker;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearInfoPage();
        colDate.setCellValueFactory(new PropertyValueFactory<>("day"));
        colKg.setCellValueFactory(new PropertyValueFactory<>("kg"));

        try {
            customerCombo.setItems(getCustomerNameList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//detect changes in the comboBox and the datePicker and then setting the info page if the changes are valid
        customerCombo.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newVal) -> {
            try {
                setInfoPage(newVal);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        datePicker.valueProperty().addListener((observableValue, localDate, newDate) -> {
            try {
                setInfoPage(newDate);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


    }

    private void setInfoPage(Object newVal) throws SQLException {
        String date = String.valueOf(datePicker.getValue());
        String id = !customerCombo.getSelectionModel().isEmpty() ? getCustId((String) (customerCombo.getValue())) : null;
        if (datePicker.getValue() != null && id != null) {
            MonthBill bill = new MonthBill(id, datePicker.getValue().getYear(), datePicker.getValue().getMonthValue());
            setDataFromBill(bill);
            setTable(bill.getKgs());

        } else {
            clearInfoPage();
        }


    }

    private void setTable(int[] kgs) {
        //create the correct data format for the table view
        ObservableList<Kgtm> kglist = FXCollections.observableArrayList();
        for (int i = 1; i < 32; i++) {
            Kgtm tempKgtm = new Kgtm(i, kgs[i - 1]);
            kglist.add(tempKgtm);
        }
        tblKg.setItems(kglist);
    }

    private void clearInfoPage() {
        lblTransport.setText("");
        lblTeaPacket.setText("");
        lblFertilizer.setText("");
        lblContainers.setText("");
        lblAdvance.setText("");
        lblBalance.setText("");
        lblWholeSub.setText("");
        lblWholeSum.setText("");
        lbltTeaSum.setText("");
        lblOtherSum.setText("");
        lblOtherSub.setText("");
        tblKg.setItems(FXCollections.observableArrayList());
    }

    //This function Will set the labels that needs to be set with values
    private void setDataFromBill(MonthBill bill) {
        lblTransport.setText(String.valueOf(bill.getKgAmount() * 5));
        lblTeaPacket.setText(String.valueOf(bill.getTeaSub()));
        lblFertilizer.setText(String.valueOf(bill.getFertilizerSub()));
        lblContainers.setText(String.valueOf(bill.getContainerSub()));
        lblAdvance.setText(String.valueOf(bill.getAdvanceSub()));
        lblBalance.setText(String.valueOf(bill.getBalance()));
        lblWholeSub.setText(String.valueOf(bill.getWholeSub()));
        lblWholeSum.setText(String.valueOf(bill.getWholeSum()));
        lbltTeaSum.setText(String.valueOf(bill.getGrossTeaSum()));
        lblOtherSum.setText(String.valueOf(bill.getOtherSum()));
        lblOtherSub.setText(String.valueOf(bill.getOtherSub()));
    }

    //getting the customer name list to the combobox to show
    private ObservableList getCustomerNameList() throws SQLException {
        String sql = "SELECT * FROM CUSTOMER";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        ObservableList<String> custNameList = FXCollections.observableArrayList();
        while (result.next()) {
            custNameList.add(result.getString(2));
        }
        return custNameList;
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

    public void addSupplier(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) tblKg.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Customer.fxml"))));

    }


//after validation update if the recExists if not insert a new rec

    public void updateOnButton(ActionEvent actionEvent) throws SQLException {
        String date = String.valueOf(datePicker.getValue());
        String id = getCustId((String) (customerCombo.getValue()));
        if (checkIfRecExists(date, id)) {
            updateRec(id, date);
        } else {
            insertRec(id, date);
        }

        setInfoPage(datePicker.getValue());

    }

    //ACTUAL INSERT METHOD THIS REALLY INSERT THE TABLE NOICE!!!!
    private void insertRec(String id, String date) throws SQLException {
        int kg = !(txtFieldKg.getText().isEmpty()) ? Integer.parseInt(txtFieldKg.getText()) : 0;
        double teaPacketC = !(txtTeaPacket.getText().isEmpty()) ? Double.parseDouble(txtTeaPacket.getText()) : 0;
        double containerC = !(txtFieldContainCost.getText().isEmpty()) ? Double.parseDouble(txtFieldContainCost.getText()) : 0;
        double fertilizerC = !(txtFertilizer.getText().isEmpty()) ? Double.parseDouble(txtFertilizer.getText()) : 0;
        double advance = !(txtAdvance.getText().isEmpty()) ? Double.parseDouble(txtAdvance.getText()) : 0;
        double otherC = !(txtOther.getText().isEmpty()) ? Double.parseDouble(txtOther.getText()) : 0;
        String sql = "INSERT INTO records VALUES('" + id + "','" + date + "'," + kg + "," + teaPacketC + "," + containerC + "," + fertilizerC + "," + otherC + "," + advance + ")";
        Statement stmt = conn.createStatement();
        int result = stmt.executeUpdate(sql);
        if (result > 0) {
            new Alert(Alert.AlertType.INFORMATION, "record added successfully");
        }


    }

    //ACTUAL UPDATE TABLE METHOD THIS REALLY UPDATES THE TABLE NOICE!!!!
    private void updateRec(String id, String date) throws SQLException {
        int kg = !(txtFieldKg.getText().isEmpty()) ? Integer.parseInt(txtFieldKg.getText()) : 0;
        double teaPacketC = !(txtTeaPacket.getText().isEmpty()) ? Double.parseDouble(txtTeaPacket.getText()) : 0;
        double containerC = !(txtFieldContainCost.getText().isEmpty()) ? Double.parseDouble(txtFieldContainCost.getText()) : 0;
        double fertilizerC = !(txtFertilizer.getText().isEmpty()) ? Double.parseDouble(txtFertilizer.getText()) : 0;
        double advance = !(txtAdvance.getText().isEmpty()) ? Double.parseDouble(txtAdvance.getText()) : 0;
        double otherC = !(txtOther.getText().isEmpty()) ? Double.parseDouble(txtOther.getText()) : 0;
        String sql = "UPDATE records SET kg=" + kg + ",teaPackets=" + teaPacketC + ",booksAndContainers=" + containerC + ",fertilizer=" + fertilizerC + ",advance=" + advance + ",other=" + otherC + "  Where id='" + id + "' AND date='" + date + "'";
        System.out.println("\n\n" + sql + "this is the sql line\n\n");
        Statement stmt = conn.createStatement();
        System.out.println("this is the update query" + sql);
        int result = stmt.executeUpdate(sql);
        if (result > 0) {
            new Alert(Alert.AlertType.INFORMATION, "record Updated successfully");
        }

    }

    //get the customer id from the selected custmer name from the combo box
    private String getCustId(String custComboValue) throws SQLException {
        String sql = "Select id from customer where name='" + custComboValue + "'";

        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        result.next();
        return result.getString(1);
    }

    //Record need to exist in order to update if not then its just gonna insert a new record to the table
    private boolean checkIfRecExists(String date, String id) throws SQLException {
        String sql = "SELECT * FROM records where date='" + date + "' AND id='" + id + "'";


        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        return result.isBeforeFirst();


    }


    public void printBill(ActionEvent actionEvent) throws SQLException, IOException, InvalidFormatException {
        String date = String.valueOf(datePicker.getValue());
        String id = !customerCombo.getSelectionModel().isEmpty() ? getCustId((String) (customerCombo.getValue())) : null;
        if (datePicker.getValue() != null && id != null) {

            MonthBill bill = new MonthBill(id, datePicker.getValue().getYear(), datePicker.getValue().getMonthValue());
            setDataFromBill(bill);
            setTable(bill.getKgs());
            MonthBill.printBill(bill);
        }
    }
}
