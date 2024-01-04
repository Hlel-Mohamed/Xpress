package com.biblio.xpress.dto;

import lombok.Data;

@Data
public class UserDTO{
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String role;
    private Long cardId;
}
