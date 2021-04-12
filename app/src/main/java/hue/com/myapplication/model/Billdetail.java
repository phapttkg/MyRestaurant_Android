package hue.com.myapplication.model;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class Billdetail {
    private int billid;
    private String id;
    private String foodname;
    private int gia;
    private int quantity;
    private String image;

    public Billdetail() {
    }


    public Billdetail(int billid, String id, String foodname, int gia, int quantity, String image) {
        this.billid = billid;
        this.id = id;
        this.foodname = foodname;
        this.gia = gia;
        this.quantity = quantity;
        this.image = image;
    }

    public int getBillid() {
        return billid;
    }

    public void setBillid(int billid) {
        this.billid = billid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
