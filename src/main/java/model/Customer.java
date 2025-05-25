package model;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private String identityNumber;

    public Customer(int id, String name, String phone, String identityNumber) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.identityNumber = identityNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }
}