package org.example.usersservice.repository;

import org.example.usersservice.models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Users, Integer> {
    boolean existsByUsername(String username);
    Optional<Users> findByUsername(String username);
}
