package org.example.police.service;

import org.example.police.exception.ResourceNotFoundException;
import org.example.police.model.RoleEnum;
import org.example.police.model.UserModel;
import org.example.police.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public UserModel findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден: " + id));
    }

    @Override
    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserModel> findByRole(RoleEnum role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    @Override
    public List<UserModel> findAllByRole(RoleEnum role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public boolean existsByEmail(String email) { return userRepository.existsByEmail(email); }

    @Override
    public boolean existsByUsername(String username) { return userRepository.existsByUsername(username); }
}