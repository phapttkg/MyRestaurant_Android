package hue.com.myapplication.model;

import java.util.List;

public class Bill {
    private String billid;
    private String date;
    private String address;
    private String payments;
    private String state;
    private int total;
    private String username;
    List<Billdetail>foods;

    public Bill() {
    }
    public Bill(String billid, String date, String address, String payments, String state, int total, String username, List<Billdetail> foods) {
        this.billid = billid;
        this.date = date;
        this.address = address;
        this.payments = payments;
        this.state = state;
        this.total = total;
        this.username = username;
        this.foods = foods;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public List<Billdetail> getFoods() {
        return foods;
    }

    public void setFoods(List<Billdetail> foods) {
        this.foods = foods;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }




    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
