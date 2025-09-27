package com.example.Interactive.Electronic.Journal.repository;

import com.example.Interactive.Electronic.Journal.entity.ConstructionSupervision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConstructionSupervisionRepository extends JpaRepository<ConstructionSupervision, Long> {

    Optional<ConstructionSupervision> findByName(String name);

}
