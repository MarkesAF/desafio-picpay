package com.augusto.repositories;

import com.augusto.domain.user.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDocument(String document);
    Optional<User> findByUserId(Long id);
}
