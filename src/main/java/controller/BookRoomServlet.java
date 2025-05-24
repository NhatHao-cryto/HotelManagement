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
import java.text.SimpleDateFormat;

public class BookRoomServlet extends HttpServlet {
    private BookingService bookingService = new BookingService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String customerName = request.getParameter("customerName");
            String phone = request.getParameter("phone");
            String identityNumber = request.getParameter("identityNumber");
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date checkInDate = sdf.parse(request.getParameter("checkInDate"));
            java.util.Date checkOutDate = sdf.parse(request.getParameter("checkOutDate"));

            BookingModel booking = bookingService.bookRoom(customerName, phone, identityNumber,
                    roomId, checkInDate, checkOutDate);

            // Hiển thị xác nhận đặt phòng với animation
            out.println("<html><head><title>Đặt Phòng Thành Công</title>");
            out.println("<style>");
            out.println("@keyframes fadeIn { 0% { opacity: 0; } 100% { opacity: 1; } }");
            out.println("body { text-align: center; animation: fadeIn 1s ease-in-out; }");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<h1>🎉 Đặt Phòng Thành Công! 🎉</h1>");
            out.println("<p>Mã đặt phòng: " + booking.getId() + "</p>");
            out.println("<p>Khách hàng: " + booking.getCustomer().getName() + "</p>");
            out.println("<p>Phòng: " + booking.getRoom().getRoomNumber() + " (" + booking.getRoom().getType() + ")</p>");
            out.println("<p>Ngày nhận: " + booking.getCheckinDate() + "</p>");
            out.println("<p>Ngày trả: " + booking.getCheckoutDate() + "</p>");
            out.println("<p>Tổng tiền: " + booking.getTotalPrice() + " VND</p>");
            out.println("<a href='/HotelManagement_war/'>🔙 Quay lại</a>");
            out.println("</body></html>");
        } catch (Exception e) {
            out.println("<html><head><title>Lỗi Đặt Phòng</title></head><body>");
            out.println("<h1>⚠️ Lỗi khi đặt phòng!</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("<a href='/HotelManagement_war/'>🔙 Quay lại</a>");
            out.println("</body></html>");
        }
    }
}