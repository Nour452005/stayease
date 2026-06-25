package com.stayease.stayease.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;


@Entity
@Table(name = "users") //without this JPA would create a table called user
// which is a reserved word in PostgreSQL
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true, nullable = false) //no two users share an email
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) //stores the role as the string ADMIN or GUEST
    private Role role;

    public enum Role{
        ADMIN, GUEST
    }
}
