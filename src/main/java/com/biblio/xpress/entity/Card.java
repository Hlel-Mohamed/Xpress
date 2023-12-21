package com.biblio.xpress.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @MapsId
    private UserEntity user;
}
