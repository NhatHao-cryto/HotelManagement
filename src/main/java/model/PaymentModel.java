package model;

import java.util.Date;

public class PaymentModel {
    private int id;
    private int bookingId;
    private double amount;
    private Date paymentDate;
    private String method; // ENUM: 'Tiền mặt', 'Chuyển khoản', 'Thẻ'

    public PaymentModel() {}

    // Getters
    public int getId() { return id; }
    public int getBookingId() { return bookingId; }
    public double getAmount() { return amount; }
    public Date getPaymentDate() { return paymentDate; }
    public String getMethod() { return method; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
    public void setMethod(String method) { this.method = method; }
}