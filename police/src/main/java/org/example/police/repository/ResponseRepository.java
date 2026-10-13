package org.example.police.repository;

import org.example.police.model.AppealModel;
import org.example.police.model.ResponseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<ResponseModel, Long> {
    List<ResponseModel> findByAppealOrderByCreatedAtAsc(AppealModel appeal);
}