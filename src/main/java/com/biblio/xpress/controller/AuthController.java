package com.biblio.xpress.controller;

import com.biblio.xpress.config.JWTGenerator;
import com.biblio.xpress.dto.LoginDTO;
import com.biblio.xpress.dto.RegisterDTO;
import com.biblio.xpress.entity.UserEntity;
import com.biblio.xpress.enums.Role;
import com.biblio.xpress.exception.EmailTakenException;
import com.biblio.xpress.exception.UsernameTakenException;
import com.biblio.xpress.mapper.UserMapper;
import com.biblio.xpress.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserMapper userMapper, JWTGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/register")
    public RedirectView register(@ModelAttribute @Valid RegisterDTO registerDTO){
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw new EmailTakenException();
        } else if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new UsernameTakenException();
        }
        UserEntity user = new UserEntity();
        user.setUsername(registerDTO.getUsername());
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@ModelAttribute LoginDTO loginDto, HttpSession session) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        session.setAttribute("token", token);
        System.out.println("Stored JWT token in session: " + token);
        UserEntity user = userRepository.findByUsername(loginDto.getUsername()).get();
        if (user.getRole() == Role.ADMIN) {
            return ResponseEntity.ok().body("admin-dashboard" + "|" + token+"|"+user.getUsername());
        } else if (user.getRole() == Role.LIBRARIAN) {
            return ResponseEntity.ok().body("librarian-dashboard" + "|" + token+"|"+user.getUsername());
        } else {
            return ResponseEntity.ok().body("member-dashboard" + "|" + token+"|"+user.getUsername());
        }
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest request) {
        return new RedirectView("/login");
    }

}
