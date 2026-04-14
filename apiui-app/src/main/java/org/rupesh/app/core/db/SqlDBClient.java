package org.rupesh.app.core.db;

import org.rupesh.app.exceptionNretry.FrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class SqlDBClient implements DBClient {

    private static final Logger log =
            LoggerFactory.getLogger(SqlDBClient.class);

    @Override
    public List<Map<String, Object>> executeQuery(String query) {

        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection connection = DBManager.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    row.put(
                            meta.getColumnName(i).toLowerCase(),
                            rs.getObject(i)
                    );
                }

                result.add(row);
            }

        } catch (Exception e) {
            log.error("DB query failed: {}", query, e);
            throw new FrameworkException("DB query failed", e);
        }

        return result;
    }

    @Override
    public int executeUpdate(String query) {

        try (Connection connection = DBManager.getConnection();
             Statement stmt = connection.createStatement()) {

            return stmt.executeUpdate(query);

        } catch (Exception e) {
            log.error("DB update failed: {}", query, e);
            throw new FrameworkException("DB update failed", e);
        }
    }
}