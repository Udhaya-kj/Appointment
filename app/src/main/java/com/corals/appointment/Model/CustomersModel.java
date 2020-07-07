package com.corals.appointment.Model;

public class CustomersModel {
    public String cus_id;
    public String name;
    public String mobile;
    public String email;

    public CustomersModel(String cus_id,String name, String mobile,String email) {
        super();
        this.cus_id = cus_id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
    }

    public String getCus_id() {
        return cus_id;
    }

    public void setCus_id(String cus_id) {
        this.cus_id = cus_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
