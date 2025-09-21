package com.example.Interactive.Electronic.Journal.repository;

import com.example.Interactive.Electronic.Journal.entity.ConstructionObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConstructionObjectRepository extends JpaRepository<ConstructionObject, Long> {

    Optional<ConstructionObject> findById(Long id);

}
