package org.example.police.repository;

import org.example.police.model.AppealTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppealTypeRepository extends JpaRepository<AppealTypeModel, Long> {
    boolean existsByName(String name);
}