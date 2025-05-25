package model;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.DriverManager.println;

public class MyConnectDB {
    String sql;
    String user;
    String password;

    public MyConnectDB() {
        this.sql = "jdbc:mysql://localhost:3306/hotel_management";
        this.user = "root";
        this.password = "";

    }
    public Connection connect() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // dùng driver mới chính xác hơn
            Connection connect = DriverManager.getConnection(this.sql, this.user, this.password);
            System.out.println("Connected to database");
            return connect;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
            throw e;
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            throw e;  // Đừng return null, ném lỗi ra để biết tại sao sai
        }
    }
    public void excuteSQL(String sql) throws Exception {
        Connection connect = connect();
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(sql);
    }
    public ResultSet corecttoDB(String sql) throws Exception {
        Connection connect = connect();
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }

    public static void main(String[] args) throws Exception {
        MyConnectDB connect = new MyConnectDB();
        System.out.println(connect.connect());
    }
}
