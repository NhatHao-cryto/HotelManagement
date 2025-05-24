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

public class BookingService {
    private BookingDAO bookingDAO = new BookingDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();


    /**
     * Kiểm tra xem phòng có trống hay không.
     */
    public boolean isRoomAvailable(int roomId, Date checkInDate, Date checkOutDate) throws SQLException {
        return bookingDAO.findConflictingBookings(roomId, checkInDate, checkOutDate).isEmpty();
    }

    /**
     * Tính tổng giá phòng dựa trên số ngày ở.
     */
    public double calculateTotalPrice(int roomId, Date checkInDate, Date checkOutDate) throws SQLException {
        RoomModel room = roomDAO.getRoomById(roomId);
        long days = TimeUnit.DAYS.convert(checkOutDate.getTime() - checkInDate.getTime(), TimeUnit.MILLISECONDS);
        return days * room.getPrice();
    }

    /**
     * Xử lý đặt phòng mới.
     */
    public BookingModel bookRoom(String customerName, String phone, String identityNumber,
                                 int roomId, Date checkInDate, Date checkOutDate) throws Exception {
        // Kiểm tra khách hàng
        CustomerModel customer = customerDAO.getCustomerByIdentityNumber(identityNumber);
        if (customer == null) {
            customer = new CustomerModel();
            customer.setName(customerName);
            customer.setPhone(phone);
            customer.setIdentityNumber(identityNumber);
            customer = customerDAO.createCustomer(customer);
        }

        // Kiểm tra phòng trống
        if (!isRoomAvailable(roomId, checkInDate, checkOutDate)) {
            throw new Exception("Phòng đã được đặt trong khoảng thời gian này.");
        }

        // Tính tổng giá phòng
        double totalPrice = calculateTotalPrice(roomId, checkInDate, checkOutDate);
        RoomModel room = roomDAO.getRoomById(roomId);

        // Tạo đặt phòng
        BookingModel booking = new BookingModel();
        booking.setCustomer(customer);
        booking.setRoom(room);
        booking.setCheckinDate(checkInDate);
        booking.setCheckoutDate(checkOutDate);
        booking.setStatus("Đặt");
        booking.setTotalPrice(totalPrice);

        bookingDAO.createBooking(booking);

        // Cập nhật trạng thái phòng
        roomDAO.updateRoomStatus(roomId, "Đã đặt");

        return booking;
    }

    /**
     * Xử lý trả phòng và tạo hóa đơn.
     */
    public PaymentModel processCheckOut(int bookingId, String paymentMethod) throws Exception {
        // Kiểm tra đơn đặt phòng
        BookingModel booking = bookingDAO.getBookingById(bookingId);
        if (booking == null || booking.getStatus().equals("Đã trả")) {
            throw new Exception("Đặt phòng không tồn tại hoặc đã được trả.");
        }

        // Kiểm tra phương thức thanh toán hợp lệ
        if (!paymentMethod.equals("Tiền mặt") && !paymentMethod.equals("Chuyển khoản") && !paymentMethod.equals("Thẻ")) {
            throw new Exception("Phương thức thanh toán không hợp lệ!");
        }

        // Cập nhật trạng thái đặt phòng
        bookingDAO.updateBookingStatus(bookingId, "Đã trả");

        // Cập nhật trạng thái phòng
        roomDAO.updateRoomStatus(booking.getRoom().getId(), "Trống");

        // Tạo hóa đơn
        PaymentModel payment = new PaymentModel();
        payment.setBookingId(bookingId);
        payment.setAmount(booking.getTotalPrice());
        payment.setPaymentDate(new Date());
        payment.setMethod(paymentMethod);
        paymentDAO.createPayment(payment);

        return payment;
    }

    public BookingModel getInvoiceByBookingId(int bookingId) throws Exception {
        BookingModel booking = bookingDAO.getBookingById(bookingId);
        if (booking == null) {
            throw new Exception("Đặt phòng không tồn tại.");
        }
        if (!"Đã trả".equals(booking.getStatus())) {
            throw new Exception("Đặt phòng chưa được thanh toán!");
        }
        return booking;
    }
}