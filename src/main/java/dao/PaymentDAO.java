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
}