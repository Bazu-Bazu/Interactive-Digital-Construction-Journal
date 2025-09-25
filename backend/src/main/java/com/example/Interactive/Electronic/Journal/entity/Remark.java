package com.example.Interactive.Electronic.Journal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "remarks")
@Data
public class Remark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    private List<Double> coordinates;

    @Column(nullable = false)
    private Boolean fixed;

    @ElementCollection
    @CollectionTable(name = "remark_files", joinColumns = @JoinColumn(name = "remark_id"))
    @Column(name = "file_url")
    private List<String> urls = new ArrayList<>();

}
