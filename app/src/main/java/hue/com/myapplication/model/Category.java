package hue.com.myapplication.model;

public class Category {
    private String categoryid;
    private String name;
    private String image;

    public Category() {
    }

    public Category(String categoryid, String name, String image) {
        this.categoryid = categoryid;
        this.name = name;
        this.image = image;
    }


    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
