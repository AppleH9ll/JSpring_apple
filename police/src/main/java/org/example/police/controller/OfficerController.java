package org.example.police.controller;

import org.example.police.model.*;
import org.example.police.service.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/officer")
@PreAuthorize("hasAnyAuthority('OFFICER','ADMIN')")
public class OfficerController {

    private final AppealService appealService;
    private final UserService userService;

    public OfficerController(AppealService appealService, UserService userService) {
        this.appealService = appealService;
        this.userService = userService;
    }

    @GetMapping("/appeals")
    public String listAppeals(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(required = false) AppealStatus status,
                              Model model) {
        var appealPage = (status != null)
                ? appealService.findByStatus(status, PageRequest.of(page, size))
                : appealService.findAll(PageRequest.of(page, size));
        model.addAttribute("appeals", appealPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", appealPage.getTotalPages());
        model.addAttribute("statuses", AppealStatus.values());
        model.addAttribute("selectedStatus", status);
        return "officer/appeals";
    }

    @PostMapping("/appeals/{id}/status")
    public String changeStatus(@PathVariable Long id,
                               @RequestParam AppealStatus status,
                               Authentication auth,
                               RedirectAttributes ra) {
        UserModel officer = userService.findByUsername(auth.getName()).orElseThrow();
        appealService.updateStatus(id, status, officer);
        ra.addFlashAttribute("successMessage", "Статус обновлён");
        return "redirect:/officer/appeals/" + id;
    }

    @PostMapping("/appeals/{id}/response")
    public String addResponse(@PathVariable Long id,
                              @RequestParam String text,
                              Authentication auth,
                              RedirectAttributes ra) {
        UserModel officer = userService.findByUsername(auth.getName()).orElseThrow();
        appealService.addResponse(id, officer, text);
        ra.addFlashAttribute("successMessage", "Ответ отправлен");
        return "redirect:/officer/appeals/" + id;
    }

    @GetMapping("/appeals/{id}")
    public String viewAppeal(@PathVariable Long id, Model model) {
        AppealModel appeal = appealService.findById(id);
        model.addAttribute("appeal", appeal);
        model.addAttribute("responses", appealService.getResponses(appeal));
        model.addAttribute("statuses", AppealStatus.values());
        return "officer/appeal-detail";
    }
}