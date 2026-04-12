package org.rupesh.test.data.repository;

import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.rupesh.test.data.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public void save(User user) throws SQLException {
        jdbc.update(
                "INSERT INTO users (id, email, status, created_at) VALUES (?, ?, ?, NOW())",
                user.getId(),
                user.getEmail(),
                "CREATED"
        );
    }

    public User findAny() throws SQLException {
        return (User) jdbc.query(
                "SELECT * FROM users LIMIT 1",
                rs -> rs.next() ? map(rs) : null
        );
    }

    public void delete(String id) throws SQLException {
        jdbc.update("DELETE FROM users WHERE id = ?", id);
    }

    private User map(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getString("id"));
        u.setEmail(rs.getString("email"));
        return u;
    }
}