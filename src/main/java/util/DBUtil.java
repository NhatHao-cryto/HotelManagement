package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static String url;
    private static String username;
    private static String password;

    static {
        try (InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("Không tìm thấy file db.properties trong classpath");
            }

            prop.load(input);

            String host = prop.getProperty("db.host");
            String port = prop.getProperty("db.port");
            String dbname = prop.getProperty("db.dbname");
            String options = prop.getProperty("db.option");
            username = prop.getProperty("db.user");
            password = prop.getProperty("db.password");

            // Kiểm tra các giá trị bắt buộc
            if (host == null || host.trim().isEmpty() ||
                    port == null || port.trim().isEmpty() ||
                    dbname == null || dbname.trim().isEmpty()) {
                throw new RuntimeException("Cấu hình DB thiếu: host, port hoặc dbname không được để trống");
            }

            // Tạo URL kết nối
            url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
            if (options != null && !options.trim().isEmpty()) {
                url += "?" + options;
            }

            // Đăng ký driver MySQL
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Đã đăng ký MySQL Driver thành công");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Không tìm thấy MySQL Driver", e);
            }

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi load cấu hình DB: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Kết nối MySQL thành công: " + url);
            return conn;
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối MySQL: " + e.getMessage());
            throw e;
        }
    }
}