package model;

import db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {
    Connection conn=DBConnection.getConnection();
    private String id;
    private String name;
    private String address;
    private String phone;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + phone +
                '}';
    }

    public Customer(String id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Customer(String id) throws SQLException {
        String sql="SELECT * FROM customer where id='"+id+"'";
        Statement stmt=conn.createStatement();
        ResultSet result=stmt.executeQuery(sql);
        if(result.next()) {
         this.id = result.getString(1);
            this.name = result.getString(2);
            this.address = result.getString(3);
            this.phone = result.getString(4);
        }
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
