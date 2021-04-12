package hue.com.myapplication.model;

public class Food {
    private String foodid;
    private String categorid;
    private String foodname;
    private int price;
    private String foodimage;

    public Food() {
    }

    public Food(String foodid, String categorid, String foodname, int price, String foodimage) {
        this.foodid = foodid;
        this.categorid = categorid;
        this.foodname = foodname;
        this.price = price;
        this.foodimage = foodimage;
    }

    public String getFoodimage() {
        return foodimage;
    }

    public void setFoodimage(String foodimage) {
        this.foodimage = foodimage;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getCategorid() {
        return categorid;
    }

    public void setCategorid(String categorid) {
        this.categorid = categorid;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
