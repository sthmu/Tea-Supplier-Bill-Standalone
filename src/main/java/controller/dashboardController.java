package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class dashboardController {


    public AnchorPane pane;
    public HBox headerPane;
    public Label time;

    public void initialize(){
        updateTime();

    }

    private void updateTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void goToCustomer(ActionEvent actionEvent) {
        Stage stage= (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Customer.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("this is not working");
            throw new RuntimeException(e);
        }
    }


    public void goToCheck(ActionEvent actionEvent) {
        Stage stage=(Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/insertData.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("dashboardController goto items Error");
            throw new RuntimeException(e);
        }
    }

    public void goToRecords(ActionEvent actionEvent) {
        System.out.println("goto Records triggered");
    }

    public void goToPrintBills(ActionEvent actionEvent) {
        Stage stage=(Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/BillPrint.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("dashboardController goto items Error");
            throw new RuntimeException(e);
        }
    }
}
