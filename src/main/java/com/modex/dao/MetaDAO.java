package com.modex.dao;

import com.modex.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MetaDAO {

    public void createMetaTable() {
        String sql = "CREATE TABLE IF NOT EXISTS app_meta (" +
                     "key TEXT PRIMARY KEY, " +
                     "value TEXT)";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isInitialized() {
        String sql = "SELECT value FROM app_meta WHERE key = 'initialized'";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void setInitialized() {
        String sql = "INSERT OR REPLACE INTO app_meta(key, value) VALUES('initialized', 'true')";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
