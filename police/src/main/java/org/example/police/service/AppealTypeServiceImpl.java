package org.example.police.service;

import org.example.police.model.AppealTypeModel;
import org.example.police.repository.AppealTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AppealTypeServiceImpl implements AppealTypeService {

    private final AppealTypeRepository appealTypeRepository;

    public AppealTypeServiceImpl(AppealTypeRepository appealTypeRepository) {
        this.appealTypeRepository = appealTypeRepository;
    }

    @Override
    public Page<AppealTypeModel> findAll(Pageable pageable) {
        return appealTypeRepository.findAll(pageable);
    }

    @Override
    public Optional<AppealTypeModel> findById(Long id) {
        return appealTypeRepository.findById(id);
    }

    @Override
    public AppealTypeModel save(AppealTypeModel type) {
        return appealTypeRepository.save(type);
    }

    @Override
    public void deleteById(Long id) {
        appealTypeRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return appealTypeRepository.existsByName(name);
    }

    @Override
    public long getTotalCount() {
        return appealTypeRepository.count();
    }
}