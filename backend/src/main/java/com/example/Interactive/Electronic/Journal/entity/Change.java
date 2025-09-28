package com.example.Interactive.Electronic.Journal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "changes")
@Data
public class Change {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean accepted;

    private String type;

    @Column(name = "proposed_start_date")
    private LocalDate proposedStartDate;

    @Column(name = "proposed_end_date")
    private LocalDate proposedEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private Part part;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "object_id")
    private ConstructionObject object;

}
