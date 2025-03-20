package org.example.repository;

import org.example.model.Role;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    List<User> findByRole(Role role);

    List<User> findByCreatedAtAfter(LocalDate createdAtAfter);

    boolean existsByUsername(String username);
}
