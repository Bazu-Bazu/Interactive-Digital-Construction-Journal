package com.example.Interactive.Electronic.Journal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "construction_objects")
@Data
public class ConstructionObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private List<Double> coordinates;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "foreman_id")
    private User foreman;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inspector_id")
    private User inspector;

    @Column(nullable = false)
    private Boolean activated;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @OneToMany(mappedBy = "object", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Part> parts = new ArrayList<>();

//    @OneToMany(mappedBy = "object_id", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Remark> remarks = new ArrayList<>();

//    @OneToMany(mappedBy = "object_id", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Change> changes = new ArrayList<>();

//    @OneToMany(mappedBy = "object_id", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Material> materials = new ArrayList<>();

//    @Column(name = "open_action check_list_url")
//    private String openActionCheckListUrl;

    @Column(name = "open_action_url")
    private String openActionUrl;

}
