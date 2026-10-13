package org.example.police.service;

import org.example.police.model.DepartmentModel;
import org.example.police.repository.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Page<DepartmentModel> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    @Override
    public Optional<DepartmentModel> findById(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public DepartmentModel save(DepartmentModel department) {
        return departmentRepository.save(department);
    }

    @Override
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Page<DepartmentModel> searchByName(String name, Pageable pageable) {
        if (name == null || name.trim().isEmpty()) {
            return departmentRepository.findAll(pageable);
        }
        return departmentRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public boolean existsByName(String name) {
        return departmentRepository.existsByName(name);
    }

    @Override
    public long getTotalCount() {
        return departmentRepository.count();
    }
}