
package controller;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import model.Customer;
import model.CustomerDAO;

public class SearchCustomerServlet extends HttpServlet {
    //4.5. Servlet xử lý và truy vấn cơ sở dữ liệu thông qua CustomerDAO.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Lấy từ khóa tìm kiếm từ request
        String keyword = request.getParameter("keyword");
        //Tạo DAO để truy xuất dữ liệu
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customers;

        try {
            //Thực hiện truy vấn tìm kiếm khách hàng
            customers = customerDAO.searchCustomers(keyword);
        } catch (Exception e) {
            customers = null;
            e.printStackTrace();
        }
        //Gửi danh sách khách hàng đến trang JSP để hiển thị
        request.setAttribute("customers", customers);
        request.getRequestDispatcher("/searchCustomer.jsp").forward(request, response);
    }
}
