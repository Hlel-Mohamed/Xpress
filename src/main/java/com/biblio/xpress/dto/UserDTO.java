package com.biblio.xpress.dto;

import com.biblio.xpress.util.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDTO{
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String role;
    private Long cardId;
}
