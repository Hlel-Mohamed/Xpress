package com.biblio.xpress.service.impl;

import com.biblio.xpress.entity.UserEntity;
import com.biblio.xpress.enums.Role;
import com.biblio.xpress.repository.UserRepository;
import com.biblio.xpress.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;



    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<UserEntity> findUserByRole(Role role){
        return userRepository.findAllByRole(role);
    }
    public UserEntity findUserById(Long id){
        return userRepository.getById(id);
    }
    public Optional<UserEntity> findUserByUsername(String username) {
        return  userRepository.findByUsername(username);

    }
    public UserEntity saveUser(UserEntity user){
        return userRepository.save(user);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}
