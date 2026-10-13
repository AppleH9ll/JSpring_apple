package org.example.police.repository;

import org.example.police.model.DepartmentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentModel, Long> {
    Page<DepartmentModel> findByNameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsByName(String name);
}