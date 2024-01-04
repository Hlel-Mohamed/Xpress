package com.biblio.xpress.service.impl;

import com.biblio.xpress.entity.UserEntity;
import com.biblio.xpress.enums.Role;
import com.biblio.xpress.repository.UserRepository;
import com.biblio.xpress.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<UserEntity> findUserByRole(Role role){
        return userRepository.findAllByRole(role);
    }
    public UserEntity findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public Optional<UserEntity> findUserByEmail(String email) {
        return  userRepository.findByEmail(email);

    }
    public UserEntity saveUser(UserEntity user){
        return userRepository.save(user);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}
