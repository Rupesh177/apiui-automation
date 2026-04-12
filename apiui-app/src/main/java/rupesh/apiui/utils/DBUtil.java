package rupesh.apiui.utils;

import rupesh.apiui.core.db.DBClient;
import rupesh.apiui.core.db.DBManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil implements DBClient {

    @Override
    public List<Map<String, Object>> executeQuery(String query) {

        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<Map<String, Object>> result = new ArrayList<>();

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    row.put(meta.getColumnName(i), rs.getObject(i));
                }

                result.add(row);
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("DB query failed", e);
        }
    }

    @Override
    public int executeUpdate(String query) {
        try (Connection conn = DBManager.getConnection();
             Statement stmt = conn.createStatement()) {

            return stmt.executeUpdate(query);

        } catch (Exception e) {
            throw new RuntimeException("DB update failed", e);
        }
    }
}