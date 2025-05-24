package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PaymentModel;
import service.BookingService;

import java.io.IOException;
import java.io.PrintWriter;

public class CheckOutServlet extends HttpServlet {
    private BookingService bookingService = new BookingService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            String paymentMethod = request.getParameter("paymentMethod");

            // Sử dụng phương thức `processCheckOut` từ `BookingService`
            PaymentModel payment = bookingService.processCheckOut(bookingId, paymentMethod);

            // Hiển thị thông báo hoàn tất với animation
            out.println("<html><head><title>Trả Phòng Thành Công</title>");
            out.println("<style>");
            out.println("@keyframes scaleUp { 0% { transform: scale(0.8); } 100% { transform: scale(1); } }");
            out.println("body { text-align: center; animation: scaleUp 0.5s ease-in-out; }");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<h1>✅ Trả Phòng Thành Công! ✅</h1>");
            out.println("<p>Mã thanh toán: " + payment.getId() + "</p>");
            out.println("<p>Mã đặt phòng: " + payment.getBookingId() + "</p>");
            out.println("<p>Số tiền: " + payment.getAmount() + " VND</p>");
            out.println("<p>Phương thức thanh toán: " + payment.getMethod() + "</p>");
            out.println("<a href=\"/HotelManagement_war/invoice?bookingId=" + payment.getBookingId() + "\">🧾 Xem Hóa Đơn</a>");
            out.println("<br><a href='/HotelManagement_war/'>🔙 Quay lại</a>");
            out.println("</body></html>");
        } catch (Exception e) {
            out.println("<html><head><title>Lỗi Khi Trả Phòng</title></head><body>");
            out.println("<h1>⚠️ Có lỗi khi trả phòng!</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("<a href='/HotelManagement_war/'>🔙 Quay lại</a>");
            out.println("</body></html>");
        }
    }
}