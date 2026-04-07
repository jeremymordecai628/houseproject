package com.modex.dao;

import com.modex.db.DBConnection;
import com.modex.model.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicesDAO {

    // Create table
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS services (" +
                     "account_no TEXT PRIMARY KEY, " +
                     "service_name TEXT NOT NULL)";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Insert
    public void insert(services s) {
        String sql = "INSERT INTO services(account_no, service_name) VALUES(?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, s.Account());
            pstmt.setString(2, s.Name());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all
    public List<services> getAll() {
        List<services> list = new ArrayList<>();
        String sql = "SELECT * FROM services";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new services(
                        rs.getString("account_no"),
                        rs.getString("service_name")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get by ServiceName
    public services getByAccount(String serviceName) {
        String sql = "SELECT * FROM services WHERE service_name = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, serviceName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new services(
                        rs.getString("account_no"),
                        rs.getString("service_name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Delete
    public void delete(String accountNo) {
        String sql = "DELETE FROM services WHERE account_no = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNo);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
