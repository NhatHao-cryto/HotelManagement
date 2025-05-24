package dao;

import model.CustomerModel;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public CustomerModel getCustomerByIdentityNumber(String identityNumber) throws SQLException {
        String sql = "SELECT * FROM users WHERE identity_number = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, identityNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CustomerModel customer = new CustomerModel();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setIdentityNumber(rs.getString("identity_number"));
                return customer;
            }
            return null;
        }
    }

    public CustomerModel getCustomerById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CustomerModel customer = new CustomerModel();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setIdentityNumber(rs.getString("identity_number"));
                return customer;
            }
            return null;
        }
    }
    public CustomerModel getCustomerByPhone(String phone) throws SQLException {
        String sql = "SELECT * FROM users WHERE phone = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CustomerModel customer = new CustomerModel();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setIdentityNumber(rs.getString("identity_number"));
                return customer;
            }
            return null;
        }
    }

    public List<CustomerModel> getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM users";
        List<CustomerModel> customers = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CustomerModel customer = new CustomerModel();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setIdentityNumber(rs.getString("identity_number"));
                customers.add(customer);
            }
            return customers;
        }
    }

    public CustomerModel createCustomer(CustomerModel customer) throws SQLException {
        String sql = "INSERT INTO users (name, phone, identity_number, email, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhone());
            stmt.setString(3, customer.getIdentityNumber());
            stmt.setString(4, "default@example.com");
            stmt.setString(5, "default_password"); // Nên dùng BCrypt trong thực tế
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                customer.setId(rs.getInt(1));
            }
            return customer;
        }
    }
}