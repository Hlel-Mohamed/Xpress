package com.biblio.xpress.dto;

import com.biblio.xpress.util.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotNull @NotEmpty @NotBlank
    private String firstName;
    @NotNull @NotEmpty @NotBlank
    private String lastName;
    private String phone;
    private String address;
    @NotNull @NotEmpty @NotBlank @Email
    private String email;
    @NotNull @ValidPassword
    private String password;
}
