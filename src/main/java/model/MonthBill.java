package model;

import db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MonthBill {
    private static Connection conn = DBConnection.getConnection();
    public static double kgPrice = 160;

    public static double getKgPrice() {
        return kgPrice;
    }

    public static void setKgPrice(double kgPrice) {
        MonthBill.kgPrice = kgPrice;
    }

    private String id;
    private String year;
    private String month;


    //SUMS
    private int[] kgs = new int[31];
    private double carrySum;
    private double grossTeaSum;
    private double otherSum;
    private double WholeSum;

    //SUBS
    private double transportSub;
    private double teaSub;
    private double containerSub;
    private double fertilizerSub;
    private double otherSub;
    private double advanceSub;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getKgAmount() {
        int tempSum = 0;
        for (int i = 0; i < 31; i++) {
            tempSum += kgs[i];
        }
        return tempSum;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int[] getKgs() {
        return kgs;
    }

    public double getCarrySum() {
        return carrySum;
    }

    public void setCarrySum(double carrySum) {
        this.carrySum = carrySum;
    }

    public double getGrossTeaSum() {
        for (int i = 0; i < 31; i++) {
            grossTeaSum += kgs[i] * kgPrice;
        }
        return grossTeaSum;
    }

    public void setGrossTeaSum(double grossTeaSum) {
        this.grossTeaSum = grossTeaSum;
    }

    public double getOtherSum() {
        return otherSum;
    }

    public void setOtherSum(double otherSum) {
        this.otherSum = otherSum;
    }


    public void setWholeSum(double wholeSum) {
        WholeSum = wholeSum;
    }

    public double getTransportSub() {
        return transportSub;
    }

    public void setTransportSub(double transportSub) {
        this.transportSub = transportSub;
    }

    public double getTeaSub() {
        return teaSub;
    }

    public void setTeaSub(double teaSub) {
        this.teaSub = teaSub;
    }

    public double getContainerSub() {
        return containerSub;
    }

    public void setContainerSub(double containerSub) {
        this.containerSub = containerSub;
    }

    public double getFertilizerSub() {
        return fertilizerSub;
    }

    public void setFertilizerSub(double fertilizerSub) {
        this.fertilizerSub = fertilizerSub;
    }

    public double getOtherSub() {
        return otherSub;
    }

    public void setOtherSub(double otherSub) {
        this.otherSub = otherSub;
    }

    public double getAdvanceSub() {
        return advanceSub;
    }

    public void setAdvanceSub(double advanceSub) {
        this.advanceSub = advanceSub;
    }

    public double getWholeSum() {

        System.out.println(grossTeaSum + "sdadasd");
        WholeSum = getCarrySum() + getGrossTeaSum() + getOtherSum();

        return WholeSum;

    }

    public double getWholeSub() {
        double wholeSub = transportSub + teaSub + containerSub + fertilizerSub + otherSub + advanceSub;

        return wholeSub;
    }

    public double getBalance() {
        return getWholeSum() - getWholeSub();
    }


    public static boolean printBill(MonthBill bill) throws SQLException {
        Customer customer = new Customer(bill.getId());


    }
}