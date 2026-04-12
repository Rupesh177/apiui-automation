package main.java.rupesh.apiui.testdata.service;

import org.springframework.stereotype.Service;
import rupesh.apiui.testdata.event.EventPublisher;
import rupesh.apiui.testdata.model.User;
import rupesh.apiui.testdata.repository.UserRepository;

import java.sql.SQLException;

@Service
public class UserDataService {

    private final UserRepository repo;
    private final EventPublisher publisher;

    public UserDataService(UserRepository repo, EventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    public User createUser() throws SQLException {

        String id = "user_" + System.currentTimeMillis();
        String email = id + "@test.com";

        User user = new User();
        user.setId(id);
        user.setEmail(email);

        // -------------------------------
        // DB INSERT
        // -------------------------------
        repo.save(user);

        // -------------------------------
        // OPTIONAL: KAFKA EVENT
        // -------------------------------
        publisher.publishUserCreated(id);

        return user;
    }

    public User getUser() throws SQLException {
        User user = repo.findAny();
        return user != null ? user : createUser();
    }

    public void deleteUser(String id) throws SQLException {
        repo.delete(id);
    }
}
