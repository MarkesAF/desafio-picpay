package com.augusto.repositories;

import com.augusto.domain.user.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {



    Optional<User> findByDocument(String document);
    Optional<User> findByUserId(Long id);
}
