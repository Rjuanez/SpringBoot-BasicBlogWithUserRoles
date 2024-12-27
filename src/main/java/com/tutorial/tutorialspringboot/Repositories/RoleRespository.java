package com.tutorial.tutorialspringboot.Repositories;

import com.tutorial.tutorialspringboot.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRespository  extends JpaRepository<Role, Long> {

    public Optional<Role> findByName(String name);
}
