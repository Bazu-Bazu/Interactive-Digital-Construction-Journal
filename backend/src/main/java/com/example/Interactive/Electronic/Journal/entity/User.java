package com.example.Interactive.Electronic.Journal.entity;

import com.example.Interactive.Electronic.Journal.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private String patronymic;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspector_supervision_id", nullable = false)
    private ConstructionSupervision inspectorSupervision;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_supervision_id", nullable = false)
    private ConstructionSupervision customerSupervision;

}
