package com.corals.appointment.Model;

public class TimeSlotDataModel {
    public String page_id;
    public String cus_id;
    public String cus;
    public String cus_email;
    public String date;
    public String ser_id;
    public String ser;
    public String service_dur;

    public String cus_mob;

    public String getService_dur() {
        return service_dur;
    }

    public void setService_dur(String service_dur) {
        this.service_dur = service_dur;
    }

    public String getCus_mob() {
        return cus_mob;
    }

    public void setCus_mob(String cus_mob) {
        this.cus_mob = cus_mob;
    }

    public String getPage_id() {
        return page_id;
    }

    public void setPage_id(String page_id) {
        this.page_id = page_id;
    }

    public String getCus_id() {
        return cus_id;
    }

    public void setCus_id(String cus_id) {
        this.cus_id = cus_id;
    }

    public String getCus() {
        return cus;
    }

    public void setCus(String cus) {
        this.cus = cus;
    }

    public String getCus_email() {
        return cus_email;
    }

    public void setCus_email(String cus_email) {
        this.cus_email = cus_email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSer_id() {
        return ser_id;
    }

    public void setSer_id(String ser_id) {
        this.ser_id = ser_id;
    }

    public String getSer() {
        return ser;
    }

    public void setSer(String ser) {
        this.ser = ser;
    }


    public TimeSlotDataModel(String page_id, String cus_id, String cus, String cus_email, String cus_mob, String date, String ser_id, String ser, String service_dur) {
        super();
        this.page_id = page_id;
        this.cus_id = cus_id;
        this.cus = cus;
        this.cus_email = cus_email;
        this.date = date;
        this.ser_id = ser_id;
        this.ser = ser;
        this.service_dur = service_dur;
        this.cus_mob = cus_mob;
    }

}
