package dao;

import model.BookingModel;
import model.CustomerModel;
import model.RoomModel;
import util.DBUtil;

import java.sql.*;

public class BookingDAO {
    public BookingModel getBookingById(int bookingId) {
        BookingModel booking = null;

        String sql = """
            SELECT 
                b.id, b.checkin_date, b.checkout_date, b.total_price,
                c.id AS customer_id, c.name, c.phone, c.identity_number,
                r.id AS room_id, r.room_number, r.type
            FROM bookings b
            JOIN customers c ON b.customer_id = c.id
            JOIN rooms r ON b.room_id = r.id
            WHERE b.id = ?
        """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    booking = new BookingModel();
                    booking.setId(rs.getInt("id"));
                    booking.setCheckinDate(rs.getDate("checkin_date"));
                    booking.setCheckoutDate(rs.getDate("checkout_date"));
                    booking.setTotalPrice(rs.getDouble("total_price"));

                    CustomerModel customer = new CustomerModel();
                    customer.setId(rs.getInt("customer_id"));
                    customer.setName(rs.getString("name"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setIdentityNumber(rs.getString("identity_number"));

                    RoomModel room = new RoomModel();
                    room.setId(rs.getInt("room_id"));
                    room.setRoomNumber(rs.getString("room_number"));
                    room.setType(rs.getString("type"));

                    booking.setCustomer(customer);
                    booking.setRoom(room);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return booking;
    }
}

