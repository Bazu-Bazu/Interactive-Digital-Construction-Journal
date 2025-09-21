package com.example.Interactive.Electronic.Journal.repository;

import com.example.Interactive.Electronic.Journal.entity.RefreshToken;
import com.example.Interactive.Electronic.Journal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);

}
