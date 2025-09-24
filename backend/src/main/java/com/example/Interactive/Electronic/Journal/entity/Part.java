package com.example.Interactive.Electronic.Journal.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "parts")
@Data
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private Boolean done;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "object_id")
    private ConstructionObject object;

//    @OneToMany(mappedBy = "object_id", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Change> changes = new ArrayList<>();

}
