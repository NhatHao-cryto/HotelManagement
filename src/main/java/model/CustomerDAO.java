package model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public List<Customer> searchCustomers(String keyword) throws Exception {
        List<Customer> customers = new ArrayList<>();
        Connection connect = new MyConnectDB().connect();
        String sql = "SELECT * FROM customer WHERE name LIKE ? OR phone LIKE ? OR identityNumber LIKE ?";
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setString(1, "%" + keyword + "%");
        stmt.setString(2, "%" + keyword + "%");
        stmt.setString(3, "%" + keyword + "%");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            customers.add(new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("phone"),
                    rs.getString("identityNumber")));
        }
        return customers;
    }
}