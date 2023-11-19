package com.biblio.xpress.entity;

import com.biblio.xpress.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    private String message;
    private Date sendDate;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
}
