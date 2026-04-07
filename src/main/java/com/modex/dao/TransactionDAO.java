package com.modex.dao;

import com.modex.db.DBConnection;
import com.modex.model.Transaction;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // Create table
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transactions (" +
                     "trans_id TEXT PRIMARY KEY, " +
                     "paymentdate TEXT NOT NULL, " +
                     "amount REAL NOT NULL, " +
                     "paid_id TEXT NOT NULL, " +
                     "balance REAL NOT NULL, " +
                     "status TEXT NOT NULL)";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Insert
    public void insert(Transaction t) {
        String sql = "INSERT INTO transactions(trans_id, paymentdate, amount, paid_id, balance, status) " +
                     "VALUES(?,?,?,?,?,?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, t.get_transid());
            pstmt.setString(2, t.get_paymentdate().toString()); // store as TEXT
            pstmt.setDouble(3, t.get_amount());
            pstmt.setString(4, t.get_paid_id());
            pstmt.setDouble(5, t.get_balance());
            pstmt.setString(6, t.get_status().name()); // enum → string

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all
    public List<Transaction> getAll() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Transaction(
                        rs.getString("trans_id"),
                        LocalDateTime.parse(rs.getString("paymentdate")),
                        rs.getDouble("amount"),
                        rs.getString("paid_id"),
                        rs.getDouble("balance"),
                        Transaction.Status.valueOf(rs.getString("status"))
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get by ID
    public Transaction getById(String id) {
        String sql = "SELECT * FROM transactions WHERE trans_id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Transaction(
                        rs.getString("trans_id"),
                        LocalDateTime.parse(rs.getString("paymentdate")),
                        rs.getDouble("amount"),
                        rs.getString("paid_id"),
                        rs.getDouble("balance"),
                        Transaction.Status.valueOf(rs.getString("status"))
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Delete
    public void delete(String id) {
        String sql = "DELETE FROM transactions WHERE trans_id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
