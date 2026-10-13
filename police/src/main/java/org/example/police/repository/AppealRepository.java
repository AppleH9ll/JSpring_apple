package org.example.police.repository;

import org.example.police.model.AppealModel;
import org.example.police.model.AppealStatus;
import org.example.police.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppealRepository extends JpaRepository<AppealModel, Long> {
    Optional<AppealModel> findByRegistrationNumber(String registrationNumber);
    Page<AppealModel> findByCitizen(UserModel citizen, Pageable pageable);
    Page<AppealModel> findByOfficer(UserModel officer, Pageable pageable);
    Page<AppealModel> findByStatus(AppealStatus status, Pageable pageable);
    Page<AppealModel> findByCitizenAndStatus(UserModel citizen, AppealStatus status, Pageable pageable);
}