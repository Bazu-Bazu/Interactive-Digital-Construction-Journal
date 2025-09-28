package com.example.Interactive.Electronic.Journal.repository;

import com.example.Interactive.Electronic.Journal.entity.Change;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeRepository extends JpaRepository<Change, Long> {



}