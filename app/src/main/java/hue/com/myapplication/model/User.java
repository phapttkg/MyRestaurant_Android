package hue.com.myapplication.model;

public class User {

   private String usname,password,fullname,phone,gender,ruler;

    public User() {
    }

    public User(String usname, String password, String fullname, String phone, String gender, String ruler) {
        this.usname = usname;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.gender = gender;
        this.ruler = ruler;
    }

    public String getUsname() {
        return usname;
    }

    public void setUsname(String usname) {
        this.usname = usname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRuler() {
        return ruler;
    }

    public void setRuler(String ruler) {
        this.ruler = ruler;
    }
}
