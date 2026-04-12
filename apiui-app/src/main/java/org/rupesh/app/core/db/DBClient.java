package org.rupesh.app.core.db;

import java.util.List;
import java.util.Map;

public interface DBClient {

    List<Map<String, Object>> executeQuery(String query);

    int executeUpdate(String query);
}