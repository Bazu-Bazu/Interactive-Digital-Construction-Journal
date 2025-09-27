package com.example.Interactive.Electronic.Journal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "construction_supervisions")
@Data
public class ConstructionSupervision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "supervision", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConstructionObject> objects = new ArrayList<>();

    @OneToMany(mappedBy = "inspectorSupervision", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> inspectors = new ArrayList<>();

    @OneToMany(mappedBy = "customerSupervision", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> customers = new ArrayList<>();

}
