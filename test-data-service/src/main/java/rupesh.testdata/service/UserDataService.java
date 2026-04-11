package rupesh.apiui.testdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import rupesh.apiui.testdata.model.User;
import rupesh.apiui.testdata.repository.UserRepository;

import java.sql.SQLException;

@Service
public class UserDataService {

    @Autowired
    private UserRepository repo;

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

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
        if (kafkaTemplate != null) {
            kafkaTemplate.send("user-created", id);
        }

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