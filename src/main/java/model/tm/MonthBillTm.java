package model.tm;

import com.jfoenix.controls.JFXRadioButton;
import model.MonthBill;

import java.time.Month;

public class MonthBillTm {
    private JFXRadioButton selectBtn;

    private MonthBill bill;
    private int year;
    private int month;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    private String id;
    private String name;
    private String address;
    private String phone;
    private double Balance;

    public MonthBillTm(MonthBill bill) {
        this.bill = bill;

        id= bill.getId();
        name=bill.getName();
        address=bill.getAddress();
        phone=bill.getPhone();
        year=bill.getYear();
        month=bill.getMonth();

    }

    public void setId(String id) {
        this.id = bill.getId();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public double getBalance() {
        return Balance;
    }

    public JFXRadioButton getSelectBtn() {
        return selectBtn;
    }

    public void setSelectBtn(JFXRadioButton selectBtn) {
        this.selectBtn = selectBtn;
    }

    public MonthBill getBill() {
        return bill;
    }

    public void setBill(MonthBill bill) {
        this.bill = bill;
    }
}
