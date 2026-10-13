package org.example.police.repository;

import org.example.police.model.RoleEnum;
import org.example.police.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Page<UserModel> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);

    @Query("SELECT u FROM UserModel u JOIN u.roles r WHERE r = :role")
    Page<UserModel> findByRole(@Param("role") RoleEnum role, Pageable pageable);

    @Query("SELECT u FROM UserModel u JOIN u.roles r WHERE r = :role")
    List<UserModel> findAllByRole(@Param("role") RoleEnum role);
}