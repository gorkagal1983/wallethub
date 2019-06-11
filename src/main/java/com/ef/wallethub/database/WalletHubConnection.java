package com.ef.wallethub.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class WalletHubConnection {

    private static WalletHubConnection walletHubConnection = null;
    private java.sql.Connection conn;

    public Connection getConn() {
        return conn;
    }

    private WalletHubConnection() {
        conn = null;

        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));

            conn = DriverManager.getConnection(
                    properties.getProperty("jdbc.mysql.url"), properties.getProperty("jdbc.mysql.username"), properties.getProperty("jdbc.mysql.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WalletHubConnection getConnection() {
        if (walletHubConnection == null)
            walletHubConnection = new WalletHubConnection();

        return walletHubConnection;
    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
