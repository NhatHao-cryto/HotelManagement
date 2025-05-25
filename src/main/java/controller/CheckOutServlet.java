package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BookingModel;
import model.PaymentModel;
import service.BookingService;

import java.io.IOException;

@WebServlet("/checkout")
public class CheckOutServlet extends HttpServlet {
    private BookingService bookingService = new BookingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingIdStr = request.getParameter("bookingId");
        if (bookingIdStr != null && bookingIdStr.matches("\\d+")) {
            try {
                int bookingId = Integer.parseInt(bookingIdStr);
                BookingModel booking = bookingService.getInvoiceByBookingId(bookingId);
                if (booking != null && !"Đã trả".equals(booking.getStatus())) {
                    request.setAttribute("bookingId", bookingId);
                    request.getRequestDispatcher("/checkout.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Đặt phòng không tồn tại hoặc đã được thanh toán!");
                    request.getRequestDispatcher("/checkOutResult.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", e.getMessage());
                request.getRequestDispatcher("/checkOutResult.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookingIdStr = request.getParameter("bookingId");
        String paymentMethod = request.getParameter("paymentMethod");

        if (bookingIdStr == null || !bookingIdStr.matches("\\d+")) {
            request.setAttribute("errorMessage", "Mã đặt phòng không hợp lệ!");
            request.getRequestDispatcher("/checkOutResult.jsp").forward(request, response);
            return;
        }

        try {
            int bookingId = Integer.parseInt(bookingIdStr);
            // Kiểm tra trạng thái đặt phòng trước khi thanh toán
            BookingModel booking = bookingService.getInvoiceByBookingId(bookingId);
            if (booking == null) {
                throw new Exception("Đặt phòng không tồn tại!");
            }
            if ("Đã trả".equals(booking.getStatus())) {
                throw new Exception("Đặt phòng đã được thanh toán!");
            }
            if (paymentMethod == null || !paymentMethod.matches("Tiền mặt|Chuyển khoản|Thẻ")) {
                throw new Exception("Phương thức thanh toán không hợp lệ!");
            }

            // Xử lý trả phòng
            PaymentModel payment = bookingService.processCheckOut(bookingId, paymentMethod);

            // Chuyển hướng tới checkOutResult.jsp (thành công)
            request.setAttribute("payment", payment);
            request.setAttribute("bookingId", bookingId); // Thêm bookingId để liên kết tới hóa đơn
            request.getRequestDispatcher("/checkOutResult.jsp").forward(request, response);

        } catch (Exception e) {
            // Chuyển hướng tới checkOutResult.jsp (lỗi)
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/checkOutResult.jsp").forward(request, response);
        }
    }
}