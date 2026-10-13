package org.example.police.service;

import org.example.police.model.AppealTypeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AppealTypeService {
    Page<AppealTypeModel> findAll(Pageable pageable);
    Optional<AppealTypeModel> findById(Long id);
    AppealTypeModel save(AppealTypeModel type);
    void deleteById(Long id);
    boolean existsByName(String name);
    long getTotalCount();
}