package org.example.police.controller;

import org.example.police.model.AppealStatus;
import org.example.police.service.AppealService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AppealService appealService;

    public HomeController(AppealService appealService) {
        this.appealService = appealService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalAppeals", appealService.getTotalCount());
        model.addAttribute("newAppeals", appealService.countByStatus(AppealStatus.NEW));
        model.addAttribute("inProgress", appealService.countByStatus(AppealStatus.IN_PROGRESS));
        model.addAttribute("reviewed", appealService.countByStatus(AppealStatus.REVIEWED));
        return "index";
    }
}