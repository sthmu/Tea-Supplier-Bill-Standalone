package model.tm;

import com.jfoenix.controls.JFXRadioButton;
import model.MonthBill;

import java.time.Month;

public class MonthBillTm {
    JFXRadioButton selectBtn;

    private MonthBill bill;

    private String id;
    private String name;
    private String Address;
    private String phone;
    private double Balance;

    public MonthBillTm(MonthBill bill) {
        this.bill = bill;

        id= bill.getId();
        name=bill.getName();
        Address=bill.getAddress();
        phone=bill.getPhone();

    }

    public void setId(String id) {
        this.id = bill.getId();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        Address = address;
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
        return Address;
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
