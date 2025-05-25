package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BookingModel;
import service.BookingService;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class BookRoomServlet extends HttpServlet {
    private BookingService bookingService = new BookingService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

            // Chuyển hướng tới bookingResult.jsp (thành công)
            request.setAttribute("booking", booking);
            request.getRequestDispatcher("/bookingResult.jsp").forward(request, response);

        } catch (Exception e) {
            // Chuyển hướng tới bookingResult.jsp (lỗi)
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/bookingResult.jsp").forward(request, response);
        }
    }
}