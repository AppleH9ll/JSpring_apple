package org.example.police.service;

import org.example.police.model.DepartmentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DepartmentService {
    Page<DepartmentModel> findAll(Pageable pageable);
    Optional<DepartmentModel> findById(Long id);
    DepartmentModel save(DepartmentModel department);
    void deleteById(Long id);
    Page<DepartmentModel> searchByName(String name, Pageable pageable);
    boolean existsByName(String name);
    long getTotalCount();
}