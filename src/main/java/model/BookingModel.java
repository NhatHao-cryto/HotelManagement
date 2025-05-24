package model;

import java.util.Date;

public class BookingModel {
    private int id;
    private CustomerModel customer;
    private RoomModel room;
    private Date checkinDate;
    private Date checkoutDate;
    private String status; // ENUM: 'Đặt', 'Đã trả'
    private double totalPrice;

    public BookingModel() {}

    // Getters
    public int getId() { return id; }
    public CustomerModel getCustomer() { return customer; }
    public RoomModel getRoom() { return room; }
    public Date getCheckinDate() { return checkinDate; }
    public Date getCheckoutDate() { return checkoutDate; }
    public String getStatus() { return status; }
    public double getTotalPrice() { return totalPrice; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setCustomer(CustomerModel customer) { this.customer = customer; }
    public void setRoom(RoomModel room) { this.room = room; }
    public void setCheckinDate(Date checkinDate) { this.checkinDate = checkinDate; }
    public void setCheckoutDate(Date checkoutDate) { this.checkoutDate = checkoutDate; }
    public void setStatus(String status) { this.status = status; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}