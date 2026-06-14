package com.example.capstoneproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user", schema = "capstone")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Size(max = 300)
    @NotNull
    @Column(name = "password", nullable = false, length = 300)
    private String password;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Cart> carts = new HashSet<>();
}