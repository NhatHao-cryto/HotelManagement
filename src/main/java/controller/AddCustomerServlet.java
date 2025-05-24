package controller;

import dao.CustomerDAO;
import model.CustomerModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AddCustomerServlet extends HttpServlet {
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String customerName = request.getParameter("customerName");
            String identityNumber = request.getParameter("identityNumber");
            String phone = request.getParameter("phone");

            // Validate CCCD
            if (!identityNumber.matches("[0-9]{12}")) {
                response.getWriter().println("CCCD phải là 12 số!");
                return;
            }

            // Validate số điện thoại
            if (!phone.matches("0[0-9]{9}")) {
                response.getWriter().println("Số điện thoại phải là 10 số, bắt đầu bằng 0!");
                return;
            }

            // Kiểm tra khách hàng tồn tại
            if (customerDAO.getCustomerByIdentityNumber(identityNumber) != null) {
                response.getWriter().println("CCCD đã tồn tại!");
                return;
            }

            // Tạo khách hàng
            CustomerModel customer = new CustomerModel();
            customer.setName(customerName);
            customer.setIdentityNumber(identityNumber);
            customer.setPhone(phone);
            customerDAO.createCustomer(customer);

            response.sendRedirect("/HotelManagement_war/home");
        } catch (SQLException e) {
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }
}