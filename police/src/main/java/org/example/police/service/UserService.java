package org.example.police.service;

import org.example.police.model.RoleEnum;
import org.example.police.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<UserModel> findAll(Pageable pageable);
    UserModel findById(Long id);
    Optional<UserModel> findByUsername(String username);
    UserModel save(UserModel user);
    void deleteById(Long id);
    Page<UserModel> findByRole(RoleEnum role, Pageable pageable);
    List<UserModel> findAllByRole(RoleEnum role);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}