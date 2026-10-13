package org.example.police.repository;

import org.example.police.model.AppealModel;
import org.example.police.model.AttachmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<AttachmentModel, Long> {
    List<AttachmentModel> findByAppeal(AppealModel appeal);
}