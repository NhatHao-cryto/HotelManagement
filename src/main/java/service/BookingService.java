package service;

import dao.BookingDAO;
import model.BookingModel;

public class BookingService {
    private BookingDAO bookingDAO = new BookingDAO();

    public BookingModel getInvoiceByBookingId(int bookingId) {
        return bookingDAO.getBookingById(bookingId);
    }
}

