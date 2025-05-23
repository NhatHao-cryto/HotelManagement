package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BookingModel;
import model.CustomerModel;
import model.RoomModel;
import service.BookingService;


import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/invoice")
public class InvoiceServlet extends HttpServlet {
    private BookingService bookingService = new BookingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingIdStr = request.getParameter("bookingId");

        if (bookingIdStr == null || bookingIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu bookingId");
            return;
        }

        int bookingId = Integer.parseInt(bookingIdStr);
        BookingModel booking = bookingService.getInvoiceByBookingId(bookingId);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (booking != null) {
                CustomerModel c = booking.getCustomer();
                RoomModel r = booking.getRoom();

                out.println("<html><head><title>Hóa đơn</title></head><body>");
                out.println("<h1>Hóa đơn thanh toán</h1>");
                out.println("<p><strong>Khách hàng:</strong> " + c.getName() + "</p>");
                out.println("<p><strong>CMND/CCCD:</strong> " + c.getIdentityNumber() + "</p>");
                out.println("<p><strong>SĐT:</strong> " + c.getPhone() + "</p>");
                out.println("<hr>");
                out.println("<p><strong>Phòng:</strong> " + r.getRoomNumber() + " (" + r.getType() + ")</p>");
                out.println("<p><strong>Ngày nhận:</strong> " + booking.getCheckinDate() + "</p>");
                out.println("<p><strong>Ngày trả:</strong> " + booking.getCheckoutDate() + "</p>");
                out.println("<p><strong>Tổng tiền:</strong> " + booking.getTotalPrice() + " VND</p>");
                out.println("</body></html>");
            } else {
                out.println("<p>Không tìm thấy hóa đơn với mã đặt phòng: " + bookingId + "</p>");
            }
        }
    }
}

