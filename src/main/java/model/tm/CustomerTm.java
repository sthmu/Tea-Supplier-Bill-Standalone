package model.tm;

import javafx.scene.control.Button;

public class CustomerTm {
    private String id;
    private String name;
    private String address;
    private String phone;
    private Button btn;

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }



    public CustomerTm(String id, String name, String address, String phone, Button btn) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.btn=btn;
    }

    @Override
    public String toString() {
        return "CustomerTm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + phone +
                ", btn=" + btn +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
