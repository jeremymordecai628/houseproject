package com.modex.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "jdbc:sqlite:com.modex.db";

    public static Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);

        // Enable best practices
        Statement stmt = conn.createStatement();
        stmt.execute("PRAGMA journal_mode=WAL;");
        stmt.execute("PRAGMA foreign_keys = ON;");

        return conn;
    }
}
