package dao;

import model.PaymentModel;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDAO {
    public PaymentModel createPayment(PaymentModel payment) throws SQLException {
        String sql = "INSERT INTO payments (booking_id, amount, payment_date, method) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, payment.getBookingId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setTimestamp(3, new java.sql.Timestamp(payment.getPaymentDate().getTime()));
            stmt.setString(4, payment.getMethod());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                payment.setId(rs.getInt(1));
            }
            return payment;
        }
    }

    public PaymentModel getPaymentByBookingId(int bookingId) throws SQLException {
        String sql = "SELECT id, booking_id, amount, payment_date, method FROM payments WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PaymentModel payment = new PaymentModel();
                payment.setId(rs.getInt("id"));
                payment.setBookingId(rs.getInt("booking_id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentDate(rs.getTimestamp("payment_date"));
                payment.setMethod(rs.getString("method"));
                return payment;
            }
            return null;
        }
    }
}