package com.biblio.xpress.entity;

import com.biblio.xpress.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Column(unique = true)
    private String email;
    private String phone;
    private String address;
    @OneToOne
    private Card card;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
