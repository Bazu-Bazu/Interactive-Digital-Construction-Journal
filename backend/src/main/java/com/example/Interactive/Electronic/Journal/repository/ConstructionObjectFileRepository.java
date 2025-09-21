package com.example.Interactive.Electronic.Journal.repository;

import com.example.Interactive.Electronic.Journal.entity.ConstructionObjectFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructionObjectFileRepository extends MongoRepository<ConstructionObjectFile, Long> {



}
