package com.biblio.xpress.controller;

import com.biblio.xpress.dto.RegisterDTO;
import com.biblio.xpress.entity.UserEntity;
import com.biblio.xpress.enums.Role;
import com.biblio.xpress.exception.EmailTakenException;
import com.biblio.xpress.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public RedirectView register(@ModelAttribute @Valid RegisterDTO registerDTO){
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw new EmailTakenException();
        }
        UserEntity user = new UserEntity();
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPhone(registerDTO.getPhone());
        user.setAddress(registerDTO.getAddress());
        user.setRole(Role.MEMBER);

        userRepository.save(user);

        return new RedirectView("/login");
    }

}
