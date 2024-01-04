package com.biblio.xpress.repository;

import com.biblio.xpress.entity.UserEntity;
import com.biblio.xpress.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity , Long> {
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
    List<UserEntity> findAllByRole(Role role);

}
