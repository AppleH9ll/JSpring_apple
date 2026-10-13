package org.example.police.service;

import org.example.police.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AppealService {
    Page<AppealModel> findAll(Pageable pageable);
    Page<AppealModel> findByCitizen(UserModel citizen, Pageable pageable);
    Page<AppealModel> findByOfficer(UserModel officer, Pageable pageable);
    Page<AppealModel> findByStatus(AppealStatus status, Pageable pageable);
    AppealModel findById(Long id);
    AppealModel findByRegistrationNumber(String number);
    AppealModel create(UserModel citizen, AppealModel appeal, List<MultipartFile> files);
    AppealModel updateStatus(Long id, AppealStatus status, UserModel officer);
    AppealModel assignOfficer(Long id, UserModel officer);
    ResponseModel addResponse(Long appealId, UserModel officer, String text);
    List<ResponseModel> getResponses(AppealModel appeal);
    long getTotalCount();
    long countByStatus(AppealStatus status);
}