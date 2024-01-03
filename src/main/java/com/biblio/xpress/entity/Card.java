package com.biblio.xpress.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnoreProperties("card")
    @JoinColumn(name = "user_id")
    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity user;
    private boolean valid =true;
    private Date startDate;
    private Date validUntil;
    private Long cardNumber;

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", valid=" + valid +
                ", startDate=" + startDate +
                ", validUntil=" + validUntil +
                ", cardNumber=" + cardNumber +
                '}';
    }
}
