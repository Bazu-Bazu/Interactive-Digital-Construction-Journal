package com.example.Interactive.Electronic.Journal.repository;

import com.example.Interactive.Electronic.Journal.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

    List<Part> findAllByObjectId(Long objectId);

}
