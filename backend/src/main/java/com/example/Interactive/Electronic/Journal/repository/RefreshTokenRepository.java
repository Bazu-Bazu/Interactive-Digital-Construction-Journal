package com.example.Interactive.Electronic.Journal.repository;

import com.example.Interactive.Electronic.Journal.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {



}
