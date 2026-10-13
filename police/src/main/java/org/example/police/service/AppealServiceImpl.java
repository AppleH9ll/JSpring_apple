package org.example.police.service;

import org.example.police.exception.ResourceNotFoundException;
import org.example.police.model.*;
import org.example.police.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;
    private final AttachmentRepository attachmentRepository;
    private final ResponseRepository responseRepository;

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    public AppealServiceImpl(AppealRepository appealRepository,
                             AttachmentRepository attachmentRepository,
                             ResponseRepository responseRepository) {
        this.appealRepository = appealRepository;
        this.attachmentRepository = attachmentRepository;
        this.responseRepository = responseRepository;
    }

    @Override
    public Page<AppealModel> findAll(Pageable pageable) { return appealRepository.findAll(pageable); }

    @Override
    public Page<AppealModel> findByCitizen(UserModel citizen, Pageable pageable) {
        return appealRepository.findByCitizen(citizen, pageable);
    }

    @Override
    public Page<AppealModel> findByOfficer(UserModel officer, Pageable pageable) {
        return appealRepository.findByOfficer(officer, pageable);
    }

    @Override
    public Page<AppealModel> findByStatus(AppealStatus status, Pageable pageable) {
        return appealRepository.findByStatus(status, pageable);
    }

    @Override
    public AppealModel findById(Long id) {
        return appealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Обращение не найдено: " + id));
    }

    @Override
    public AppealModel findByRegistrationNumber(String number) {
        return appealRepository.findByRegistrationNumber(number)
                .orElseThrow(() -> new ResourceNotFoundException("Обращение не найдено: " + number));
    }

    @Override
    public AppealModel create(UserModel citizen, AppealModel appeal, List<MultipartFile> files) {
        appeal.setCitizen(citizen);
        appeal.setStatus(AppealStatus.NEW);
        appeal.setCreatedAt(LocalDateTime.now());
        appeal.setRegistrationNumber(generateRegNumber());
        AppealModel saved = appealRepository.save(appeal);

        if (files != null && !files.isEmpty()) {
            saveFiles(saved, files);
        }
        return saved;
    }

    private String generateRegNumber() {
        String year = String.valueOf(LocalDateTime.now().getYear());
        long count = appealRepository.count() + 1;
        return String.format("ОБ-%s-%06d", year, count);
    }

    private void saveFiles(AppealModel appeal, List<MultipartFile> files) {
        try {
            Path dir = Paths.get(uploadDir);
            if (!Files.exists(dir)) Files.createDirectories(dir);

            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;
                String original = file.getOriginalFilename();
                String ext = original != null && original.contains(".")
                        ? original.substring(original.lastIndexOf('.')) : "";
                String storedName = UUID.randomUUID() + ext;
                Path target = dir.resolve(storedName);
                Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

                AttachmentModel att = new AttachmentModel();
                att.setAppeal(appeal);
                att.setFileName(original);
                att.setFilePath(target.toString());
                att.setFileSize(file.getSize());
                att.setContentType(file.getContentType());
                attachmentRepository.save(att);
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения файла: " + e.getMessage(), e);
        }
    }

    @Override
    public AppealModel updateStatus(Long id, AppealStatus status, UserModel officer) {
        AppealModel appeal = findById(id);
        appeal.setStatus(status);
        appeal.setUpdatedAt(LocalDateTime.now());
        if (officer != null && appeal.getOfficer() == null) {
            appeal.setOfficer(officer);
        }
        return appealRepository.save(appeal);
    }

    @Override
    public AppealModel assignOfficer(Long id, UserModel officer) {
        AppealModel appeal = findById(id);
        appeal.setOfficer(officer);
        if (appeal.getStatus() == AppealStatus.NEW) {
            appeal.setStatus(AppealStatus.IN_PROGRESS);
        }
        appeal.setUpdatedAt(LocalDateTime.now());
        return appealRepository.save(appeal);
    }

    @Override
    public ResponseModel addResponse(Long appealId, UserModel officer, String text) {
        AppealModel appeal = findById(appealId);
        ResponseModel response = new ResponseModel();
        response.setAppeal(appeal);
        response.setOfficer(officer);
        response.setText(text);
        response.setCreatedAt(LocalDateTime.now());
        appeal.setUpdatedAt(LocalDateTime.now());
        return responseRepository.save(response);
    }

    @Override
    public List<ResponseModel> getResponses(AppealModel appeal) {
        return responseRepository.findByAppealOrderByCreatedAtAsc(appeal);
    }

    @Override
    public long getTotalCount() { return appealRepository.count(); }

    @Override
    public long countByStatus(AppealStatus status) {
        return appealRepository.findByStatus(status, Pageable.unpaged()).getTotalElements();
    }
}