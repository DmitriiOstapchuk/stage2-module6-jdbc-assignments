package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomConnector {
    private final ResourceBundle bundle = ResourceBundle.getBundle("app");
    public Connection getConnection(String url) {
        return getConnection(url, null, null);
    }

    public Connection getConnection(String url, String user, String password)  {
        try {
            Class.forName(bundle.getString("postgres.driver"));
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
