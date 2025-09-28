package com.example.Interactive.Electronic.Journal.repository;

import com.example.Interactive.Electronic.Journal.entity.ConstructionObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConstructionObjectRepository extends JpaRepository<ConstructionObject, Long> {

    Optional<ConstructionObject> findById(Long id);
    List<ConstructionObject> findAllBySupervisionId(Long id);
    List<ConstructionObject> findAllByForemanId(Long foremanId);

    @Query(value = "SELECT * FROM construction_objects ORDER BY start_date ASC LIMIT ?1", nativeQuery = true)
    List<ConstructionObject> findTopNByOrderByStartDateAsc(int count);

}
