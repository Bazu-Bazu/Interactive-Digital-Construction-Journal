package com.example.Interactive.Electronic.Journal.repository;

import com.example.Interactive.Electronic.Journal.entity.ConstructionObjectFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConstructionObjectFileRepository extends MongoRepository<ConstructionObjectFile, Long> {

    Optional<ConstructionObjectFile> findByUrl(String url);

}
