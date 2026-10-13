package org.example.police.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.police.dto.AppealRequest;
import org.example.police.model.*;
import org.example.police.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appeals")
@Tag(name = "Обращения", description = "API для работы с обращениями граждан")
@SecurityRequirement(name = "bearerAuth")
public class AppealRestController {

    private final AppealService appealService;
    private final AppealTypeService typeService;
    private final DepartmentService departmentService;
    private final UserService userService;

    public AppealRestController(AppealService appealService, AppealTypeService typeService,
                                DepartmentService departmentService, UserService userService) {
        this.appealService = appealService;
        this.typeService = typeService;
        this.departmentService = departmentService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('OFFICER','ADMIN')")
    @Operation(summary = "Список всех обращений (для сотрудников)")
    public Page<AppealModel> list(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return appealService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/my")
    @Operation(summary = "Мои обращения")
    public Page<AppealModel> my(Authentication auth,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        UserModel user = userService.findByUsername(auth.getName()).orElseThrow();
        return appealService.findByCitizen(user, PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить обращение по ID")
    public AppealModel get(@PathVariable Long id) {
        return appealService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Создать обращение")
    public ResponseEntity<AppealModel> create(@RequestBody AppealRequest req, Authentication auth) {
        UserModel citizen = userService.findByUsername(auth.getName()).orElseThrow();
        AppealModel appeal = new AppealModel();
        appeal.setSubject(req.getSubject());
        appeal.setText(req.getText());
        typeService.findById(req.getTypeId()).ifPresent(appeal::setType);
        if (req.getDepartmentId() != null) {
            departmentService.findById(req.getDepartmentId()).ifPresent(appeal::setDepartment);
        }
        AppealModel saved = appealService.create(citizen, appeal, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('OFFICER','ADMIN')")
    @Operation(summary = "Изменить статус обращения")
    public AppealModel updateStatus(@PathVariable Long id,
                                    @RequestParam AppealStatus status,
                                    Authentication auth) {
        UserModel officer = userService.findByUsername(auth.getName()).orElseThrow();
        return appealService.updateStatus(id, status, officer);
    }
}