package model;

public class RoomModel {
    private int id;
    private String roomNumber;
    private String type;
    private double price;
    private String status; // ENUM: 'Trống', 'Đã đặt'

    public RoomModel() {}

    // Getters
    public int getId() { return id; }
    public String getRoomNumber() { return roomNumber; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setType(String type) { this.type = type; }
    public void setPrice(double price) { this.price = price; }
    public void setStatus(String status) { this.status = status; }
}