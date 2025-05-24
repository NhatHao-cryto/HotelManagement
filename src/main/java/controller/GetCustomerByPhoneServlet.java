package controller;

import dao.CustomerDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CustomerModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class GetCustomerByPhoneServlet extends HttpServlet {
    private CustomerDAO customerDAO = new CustomerDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String phone = request.getParameter("phone");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            CustomerModel customer = customerDAO.getCustomerByPhone(phone);
            if (customer != null) {
                out.print("{ \"name\": \"" + customer.getName() + "\", \"identityNumber\": \"" + customer.getIdentityNumber() + "\" }");
            } else {
                out.print("{}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("{}");
        }
        out.flush();
    }
}