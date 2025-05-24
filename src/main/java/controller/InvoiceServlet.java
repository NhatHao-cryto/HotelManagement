package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BookingModel;
import service.BookingService;


import java.io.IOException;

@WebServlet("/invoice")
public class InvoiceServlet extends HttpServlet {
    private BookingService bookingService = new BookingService();

    @Override
    public void init() throws ServletException {
        bookingService = new BookingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String bookingIdParam = request.getParameter("id");

            if (bookingIdParam == null || bookingIdParam.isEmpty()) {
                request.setAttribute("error", "Mã đặt phòng không hợp lệ!");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            int bookingId = Integer.parseInt(bookingIdParam);
            BookingModel booking = bookingService.getInvoiceByBookingId(bookingId);

            if (booking == null) {
                request.setAttribute("error", "Không tìm thấy hóa đơn với mã: " + bookingId);
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            request.setAttribute("message", "In hóa đơn thành công!");
            request.setAttribute("booking", booking);
            request.getRequestDispatcher("invoice.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi khi xử lý hóa đơn.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}

