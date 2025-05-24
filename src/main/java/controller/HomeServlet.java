package controller;

import dao.BookingDAO;
import dao.CustomerDAO;
import dao.RoomDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class HomeServlet extends HttpServlet {
    private RoomDAO roomDAO;
    private CustomerDAO customerDAO;
    private BookingDAO bookingDAO;

    @Override
    public void init() throws ServletException {
        roomDAO = new RoomDAO();
        customerDAO = new CustomerDAO();
        bookingDAO = new BookingDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("availableRooms", roomDAO.getAvailableRooms());
            request.setAttribute("customers", customerDAO.getAllCustomers());
            request.setAttribute("activeBookings", bookingDAO.getActiveBookings());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (SQLException e) {
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }
}