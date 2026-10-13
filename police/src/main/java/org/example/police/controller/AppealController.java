package org.example.police.controller;

import org.example.police.model.*;
import org.example.police.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/appeals")
public class AppealController {

    private final AppealService appealService;
    private final AppealTypeService typeService;
    private final DepartmentService departmentService;
    private final UserService userService;

    public AppealController(AppealService appealService, AppealTypeService typeService,
                            DepartmentService departmentService, UserService userService) {
        this.appealService = appealService;
        this.typeService = typeService;
        this.departmentService = departmentService;
        this.userService = userService;
    }

    @GetMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public String newAppealForm(Model model) {
        model.addAttribute("types", typeService.findAll(PageRequest.of(0, 100)).getContent());
        model.addAttribute("departments", departmentService.findAll(PageRequest.of(0, 100)).getContent());
        return "appeal-form";
    }

    @PostMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public String submitAppeal(@RequestParam String subject,
                               @RequestParam String text,
                               @RequestParam Long typeId,
                               @RequestParam(required = false) Long departmentId,
                               @RequestParam(required = false) List<MultipartFile> files,
                               Authentication auth,
                               RedirectAttributes ra) {
        UserModel citizen = userService.findByUsername(auth.getName()).orElseThrow();

        AppealModel appeal = new AppealModel();
        appeal.setSubject(subject);
        appeal.setText(text);
        typeService.findById(typeId).ifPresent(appeal::setType);
        if (departmentId != null) {
            departmentService.findById(departmentId).ifPresent(appeal::setDepartment);
        }

        AppealModel saved = appealService.create(citizen, appeal, files);
        ra.addFlashAttribute("successMessage",
                "Обращение зарегистрировано. Номер: " + saved.getRegistrationNumber());
        return "redirect:/appeals/my";
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public String myAppeals(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Authentication auth, Model model) {
        UserModel citizen = userService.findByUsername(auth.getName()).orElseThrow();
        Page<AppealModel> appealPage = appealService.findByCitizen(citizen, PageRequest.of(page, size));
        model.addAttribute("appeals", appealPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", appealPage.getTotalPages());
        return "my-appeals";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String viewAppeal(@PathVariable Long id, Authentication auth, Model model) {
        AppealModel appeal = appealService.findById(id);
        UserModel user = userService.findByUsername(auth.getName()).orElseThrow();

        boolean isOwner = appeal.getCitizen().getIdUser().equals(user.getIdUser());
        boolean isStaff = user.getRoles().contains(RoleEnum.OFFICER) || user.getRoles().contains(RoleEnum.ADMIN);

        if (!isOwner && !isStaff) {
            return "redirect:/access-denied";
        }

        model.addAttribute("appeal", appeal);
        model.addAttribute("responses", appealService.getResponses(appeal));
        return "appeal-view";
    }

    @GetMapping("/status")
    public String statusForm() { return "appeal-status"; }

    @PostMapping("/status")
    public String statusCheck(@RequestParam String regNumber, Model model) {
        try {
            AppealModel appeal = appealService.findByRegistrationNumber(regNumber);
            model.addAttribute("appeal", appeal);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Обращение с таким номером не найдено");
        }
        return "appeal-status";
    }
}