package model;

public class CustomerModel {
    private int id;
    private String name;
    private String phone;
    private String identityNumber;

    public CustomerModel() {}

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getIdentityNumber() { return identityNumber; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setIdentityNumber(String identityNumber) { this.identityNumber = identityNumber; }
}