package dao;

import model.BookingModel;
import model.CustomerModel;
import model.RoomModel;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingDAO {
    private CustomerDAO customerDAO = new CustomerDAO();
    private RoomDAO roomDAO = new RoomDAO();

    public BookingModel getBookingById(int bookingId) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BookingModel booking = new BookingModel();
                booking.setId(rs.getInt("id"));
                booking.setCustomer(customerDAO.getCustomerById(rs.getInt("customer_id")));
                booking.setRoom(roomDAO.getRoomById(rs.getInt("room_id")));
                booking.setCheckinDate(rs.getDate("checkin_date"));
                booking.setCheckoutDate(rs.getDate("checkout_date"));
                booking.setStatus(rs.getString("status"));
                booking.setTotalPrice(rs.getDouble("total_price"));
                return booking;
            }
            return null;
        }
    }

    public BookingModel createBooking(BookingModel booking) throws SQLException {
        String sql = "INSERT INTO bookings (customer_id, room_id, checkin_date, checkout_date, status, total_price) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Log kiểm tra trước khi thực thi
            System.out.println("Bắt đầu lưu đơn đặt phòng...");
            System.out.println("Khách hàng ID: " + booking.getCustomer().getId());
            System.out.println("Phòng ID: " + booking.getRoom().getId());
            System.out.println("Ngày nhận: " + booking.getCheckinDate());
            System.out.println("Ngày trả: " + booking.getCheckoutDate());
            System.out.println("Trạng thái: " + booking.getStatus());
            System.out.println("Tổng tiền: " + booking.getTotalPrice());

            stmt.setInt(1, booking.getCustomer().getId());
            stmt.setInt(2, booking.getRoom().getId());
            stmt.setDate(3, new java.sql.Date(booking.getCheckinDate().getTime()));
            stmt.setDate(4, new java.sql.Date(booking.getCheckoutDate().getTime()));
            stmt.setString(5, booking.getStatus());
            stmt.setDouble(6, booking.getTotalPrice());

            int affectedRows = stmt.executeUpdate();

            // Log kiểm tra sau khi thực thi
            if (affectedRows > 0) {
                System.out.println("Đơn đặt phòng đã lưu thành công!");
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    booking.setId(rs.getInt(1));
                    System.out.println("Mã đặt phòng mới: " + booking.getId());
                }
            } else {
                System.err.println("Lỗi: Không có dòng nào được chèn vào bảng bookings!");
            }
            return booking;

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lưu đặt phòng: " + e.getMessage());
            throw e;
        }
    }

    public void updateBookingStatus(int bookingId, String status) throws SQLException {
        String sql = "UPDATE bookings SET status = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            stmt.executeUpdate();
        }
    }

    public List<BookingModel> findConflictingBookings(int roomId, Date checkInDate, Date checkOutDate) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE room_id = ? AND status = 'Đặt' AND (checkin_date <= ? AND checkout_date >= ?)";
        List<BookingModel> bookings = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            stmt.setDate(2, new java.sql.Date(checkOutDate.getTime()));
            stmt.setDate(3, new java.sql.Date(checkInDate.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BookingModel booking = new BookingModel();
                booking.setId(rs.getInt("id"));
                booking.setCustomer(customerDAO.getCustomerById(rs.getInt("customer_id")));
                booking.setRoom(roomDAO.getRoomById(rs.getInt("room_id")));
                booking.setCheckinDate(rs.getDate("checkin_date"));
                booking.setCheckoutDate(rs.getDate("checkout_date"));
                booking.setStatus(rs.getString("status"));
                booking.setTotalPrice(rs.getDouble("total_price"));
                bookings.add(booking);
            }
            return bookings;
        }
    }

    public List<BookingModel> getActiveBookings() throws SQLException {
        String sql = "SELECT b.id, b.customer_id, b.room_id, b.checkin_date, b.checkout_date, b.status, b.total_price " +
                "FROM bookings b " +
                "WHERE b.status = 'Đặt' AND b.checkout_date >= CURDATE()";
        List<BookingModel> bookings = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Thực thi truy vấn lấy đặt phòng đang hoạt động");
            while (rs.next()) {
                BookingModel booking = new BookingModel();
                booking.setId(rs.getInt("id"));
                booking.setCustomer(customerDAO.getCustomerById(rs.getInt("customer_id")));
                booking.setRoom(roomDAO.getRoomById(rs.getInt("room_id")));
                booking.setCheckinDate(rs.getDate("checkin_date"));
                booking.setCheckoutDate(rs.getDate("checkout_date"));
                booking.setStatus(rs.getString("status"));
                booking.setTotalPrice(rs.getDouble("total_price"));
                bookings.add(booking);
                System.out.println("Đặt phòng: Mã " + booking.getId() + ", Phòng " + booking.getRoom().getRoomNumber());
            }
            System.out.println("Tổng số đặt phòng: " + bookings.size());
            return bookings;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong getActiveBookings: " + e.getMessage());
            throw e;
        }
    }
}