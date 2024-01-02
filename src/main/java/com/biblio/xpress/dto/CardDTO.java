package com.biblio.xpress.dto;

import com.biblio.xpress.entity.UserEntity;

import java.util.Date;

public class CardDTO {
    private Long id;
    private boolean valid;
    private Date startDate;
    private Date validUntil;
    private Long cardNumber;
    private UserEntity user;
}
