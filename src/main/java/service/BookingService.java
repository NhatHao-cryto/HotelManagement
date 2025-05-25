package service;

import dao.BookingDAO;
import dao.CustomerDAO;
import dao.PaymentDAO;
import dao.RoomDAO;
import model.BookingModel;
import model.CustomerModel;
import model.PaymentModel;
import model.RoomModel;

import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BookingService {
    private static final Logger LOGGER = Logger.getLogger(BookingService.class.getName());
    private BookingDAO bookingDAO = new BookingDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();

    public boolean isRoomAvailable(int roomId, Date checkInDate, Date checkOutDate) throws SQLException {
        LOGGER.info("Kiểm tra phòng trống: roomId=" + roomId + ", checkIn=" + checkInDate + ", checkOut=" + checkOutDate);
        return bookingDAO.findConflictingBookings(roomId, checkInDate, checkOutDate).isEmpty();
    }

    public double calculateTotalPrice(int roomId, Date checkInDate, Date checkOutDate) throws SQLException {
        RoomModel room = roomDAO.getRoomById(roomId);
        if (room == null) {
            throw new SQLException("Phòng không tồn tại: roomId=" + roomId);
        }
        long days = TimeUnit.DAYS.convert(checkOutDate.getTime() - checkInDate.getTime(), TimeUnit.MILLISECONDS);
        if (days <= 0) {
            throw new IllegalArgumentException("Ngày trả phòng phải sau ngày nhận phòng.");
        }
        double totalPrice = days * room.getPrice();
        LOGGER.info("Tính tổng giá: roomId=" + roomId + ", days=" + days + ", totalPrice=" + totalPrice);
        return totalPrice;
    }

    public BookingModel bookRoom(String customerName, String phone, String identityNumber,
                                 int roomId, Date checkInDate, Date checkOutDate) throws Exception {
        LOGGER.info("Bắt đầu đặt phòng: customerName=" + customerName + ", roomId=" + roomId);

        CustomerModel customer = customerDAO.getCustomerByIdentityNumber(identityNumber);
        if (customer == null) {
            customer = new CustomerModel();
            customer.setName(customerName);
            customer.setPhone(phone);
            customer.setIdentityNumber(identityNumber);
            customer = customerDAO.createCustomer(customer);
            LOGGER.info("Tạo khách hàng mới: identityNumber=" + identityNumber);
        }

        if (!isRoomAvailable(roomId, checkInDate, checkOutDate)) {
            throw new Exception("Phòng đã được đặt trong khoảng thời gian này.");
        }

        double totalPrice = calculateTotalPrice(roomId, checkInDate, checkOutDate);
        RoomModel room = roomDAO.getRoomById(roomId);

        BookingModel booking = new BookingModel();
        booking.setCustomer(customer);
        booking.setRoom(room);
        booking.setCheckinDate(checkInDate);
        booking.setCheckoutDate(checkOutDate);
        booking.setStatus("Đặt");
        booking.setTotalPrice(totalPrice);

        bookingDAO.createBooking(booking);
        LOGGER.info("Đặt phòng thành công: bookingId=" + booking.getId());

        roomDAO.updateRoomStatus(roomId, "Đã đặt");

        return booking;
    }

    public PaymentModel processCheckOut(int bookingId, String paymentMethod) throws Exception {
        LOGGER.info("Xử lý trả phòng: bookingId=" + bookingId + ", paymentMethod=" + paymentMethod);

        BookingModel booking = bookingDAO.getBookingById(bookingId);
        if (booking == null) {
            throw new Exception("Đặt phòng không tồn tại: bookingId=" + bookingId);
        }
        if (booking.getStatus().equals("Đã trả")) {
            throw new Exception("Đặt phòng đã được trả: bookingId=" + bookingId);
        }
        if (!booking.getRoom().getStatus().equals("Đã đặt")) {
            throw new Exception("Phòng không ở trạng thái 'Đã đặt': roomId=" + booking.getRoom().getId());
        }

        if (!paymentMethod.equals("Tiền mặt") && !paymentMethod.equals("Chuyển khoản") && !paymentMethod.equals("Thẻ")) {
            throw new Exception("Phương thức thanh toán không hợp lệ: " + paymentMethod);
        }

        bookingDAO.updateBookingStatus(bookingId, "Đã trả");
        LOGGER.info("Cập nhật trạng thái đặt phòng thành 'Đã trả': bookingId=" + bookingId);

        roomDAO.updateRoomStatus(booking.getRoom().getId(), "Trống");
        LOGGER.info("Cập nhật trạng thái phòng thành 'Trống': roomId=" + booking.getRoom().getId());

        PaymentModel payment = new PaymentModel();
        payment.setBookingId(bookingId);
        payment.setAmount(booking.getTotalPrice());
        payment.setPaymentDate(new Date());
        payment.setMethod(paymentMethod);
        paymentDAO.createPayment(payment);
        LOGGER.info("Tạo hóa đơn thành công: paymentId=" + payment.getId());

        return payment;
    }

    public BookingModel getInvoiceByBookingId(int bookingId) throws Exception {
        LOGGER.info("Lấy thông tin hóa đơn: bookingId=" + bookingId);
        BookingModel booking = bookingDAO.getBookingById(bookingId);
        if (booking == null) {
            throw new Exception("Đặt phòng không tồn tại: bookingId=" + bookingId);
        }
        LOGGER.info("Tìm thấy đặt phòng: bookingId=" + bookingId + ", status=" + booking.getStatus());
        return booking;
    }

    public PaymentModel getPaymentByBookingId(int bookingId) throws SQLException {
        LOGGER.info("Lấy thông tin thanh toán: bookingId=" + bookingId);
        PaymentModel payment = paymentDAO.getPaymentByBookingId(bookingId);
        if (payment == null) {
            LOGGER.warning("Không tìm thấy thanh toán cho bookingId=" + bookingId);
        }
        return payment;
    }
}