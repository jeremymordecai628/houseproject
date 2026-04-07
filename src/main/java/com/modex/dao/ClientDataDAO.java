package com.modex.dao;

import com.modex.db.DBConnection;
import com.modex.model.ClientData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDataDAO {

    // Create table
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS client_data (" +
                     "id TEXT PRIMARY KEY, " +
                     "name TEXT NOT NULL, " +
		     "downloadid TEXT NOT NULL)";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Insert
    public void insert(ClientData client) {
        String sql = "INSERT INTO client_data(id, name) VALUES(?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, client.getId());
            pstmt.setString(2, client.getName());
	    pstmt.setString(3, client.getDownloadID());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all
    public List<ClientData> getAll() {
        List<ClientData> list = new ArrayList<>();
        String sql = "SELECT * FROM client_data";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new ClientData(
                        rs.getString("id"),
                        rs.getString("name"),
			rs.getString("downloadid")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get by ID
    public ClientData getById(String id) {
        String sql = "SELECT * FROM client_data WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new ClientData(
                        rs.getString("id"),
                        rs.getString("name"),
			rs.getString("downloadid")

                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Delete
    public void delete(String id) {
        String sql = "DELETE FROM client_data WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
