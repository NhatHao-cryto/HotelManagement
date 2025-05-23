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
            if (input != null) {
                prop.load(input);

                String host = prop.getProperty("db.host");
                String port = prop.getProperty("db.port");
                String dbname = prop.getProperty("db.dbname");
                String options = prop.getProperty("db.option");
                username = prop.getProperty("db.user");
                password = prop.getProperty("db.password");

                url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;

                if (options != null && !options.isEmpty()) {
                    url += "?" + options;
                }

            } else {
                throw new RuntimeException("Không tìm thấy file db.properties");
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi load cấu hình DB", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

