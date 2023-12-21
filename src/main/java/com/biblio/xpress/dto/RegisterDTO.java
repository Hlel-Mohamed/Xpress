package com.biblio.xpress.dto;

import com.biblio.xpress.util.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotNull @NotEmpty @NotBlank
    private String firstName;
    @NotNull @NotEmpty @NotBlank
    private String lastName;
    @NotNull @NotEmpty @NotBlank
    private String username;
    private String phone;
    private String address;
    @NotNull @NotEmpty @NotBlank @Email
    private String email;
    @NotNull @ValidPassword
    private String password;
}
