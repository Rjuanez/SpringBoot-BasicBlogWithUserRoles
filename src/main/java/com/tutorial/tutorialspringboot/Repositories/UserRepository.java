package com.tutorial.tutorialspringboot.Repositories;

import com.tutorial.tutorialspringboot.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsernameOrEmail(String username, String email);
    public Optional<User> findByEmail(String email);
    public Optional<User> findByUsername(String username);
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
}
