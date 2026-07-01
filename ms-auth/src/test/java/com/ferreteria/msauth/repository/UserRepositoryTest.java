package com.ferreteria.msauth.repository;

import com.ferreteria.msauth.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByUsername() {
        User user = User.builder()
                .username("testuser")
                .password("encoded")
                .build();

        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("testuser");

        assertTrue(found.isPresent());
    }

    @Test
    void shouldCheckIfUsernameExists() {
        User user = User.builder()
                .username("anotheruser")
                .password("encoded")
                .build();

        userRepository.save(user);

        assertTrue(userRepository.existsByUsername("anotheruser"));
    }
}
