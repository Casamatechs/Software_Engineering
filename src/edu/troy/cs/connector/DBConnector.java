package edu.troy.cs.connector;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

    private static Connection connection = null;

    public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        if (connection != null) return connection;
        else {
            Properties properties = new Properties();
            InputStream input = new FileInputStream("dbdata.properties");
            properties.load(input);
            connection = setConnection("com.mysql.jdbc.Driver", properties.getProperty("host"), properties.getProperty("port"), properties.getProperty("db"), properties.getProperty("user"), properties.getProperty("password"));
            return connection;
        }
    }

    private static Connection setConnection(String driver, String host, String port, String bd, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        String url = "jdbc:mysql://".concat(host).concat(":").concat(port).concat("/").concat(bd);
        return DriverManager.getConnection(url, user, password);
    }

    public static void disconnect() {
        connection = null;
    }
}
