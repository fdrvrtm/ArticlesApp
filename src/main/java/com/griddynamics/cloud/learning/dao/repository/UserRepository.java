package com.griddynamics.cloud.learning.dao.repository;

import com.griddynamics.cloud.learning.dao.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
}
