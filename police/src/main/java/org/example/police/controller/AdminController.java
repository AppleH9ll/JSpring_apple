package org.example.police.controller;

import org.example.police.model.*;
import org.example.police.service.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final AppealTypeService typeService;
    private final DepartmentService departmentService;
    private final AppealService appealService;

    public AdminController(UserService userService, AppealTypeService typeService,
                           DepartmentService departmentService, AppealService appealService) {
        this.userService = userService;
        this.typeService = typeService;
        this.departmentService = departmentService;
        this.appealService = appealService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.findAll(PageRequest.of(0, 1)).getTotalElements());
        model.addAttribute("totalAppeals", appealService.getTotalCount());
        model.addAttribute("newAppeals", appealService.countByStatus(AppealStatus.NEW));
        model.addAttribute("inProgress", appealService.countByStatus(AppealStatus.IN_PROGRESS));
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size,
                        Model model) {
        var usersPage = userService.findAll(PageRequest.of(page, size));
        model.addAttribute("users", usersPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        return "admin/users";
    }

    @PostMapping("/users/{id}/toggle")
    public String toggleUser(@PathVariable Long id, RedirectAttributes ra) {
        UserModel u = userService.findById(id);
        u.setActive(!u.isActive());
        userService.save(u);
        ra.addFlashAttribute("successMessage", "Статус пользователя изменён");
        return "redirect:/admin/users";
    }

    @GetMapping("/types")
    public String types(Model model) {
        model.addAttribute("types", typeService.findAll(PageRequest.of(0, 100)).getContent());
        return "admin/types";
    }

    @PostMapping("/types/add")
    public String addType(@RequestParam String name, @RequestParam(required = false) String description,
                          RedirectAttributes ra) {
        AppealTypeModel t = new AppealTypeModel();
        t.setName(name);
        t.setDescription(description);
        typeService.save(t);
        ra.addFlashAttribute("successMessage", "Тип добавлен");
        return "redirect:/admin/types";
    }

    @PostMapping("/types/delete")
    public String deleteType(@RequestParam Long id, RedirectAttributes ra) {
        typeService.deleteById(id);
        ra.addFlashAttribute("successMessage", "Тип удалён");
        return "redirect:/admin/types";
    }

    @GetMapping("/departments")
    public String departments(Model model) {
        model.addAttribute("departments", departmentService.findAll(PageRequest.of(0, 100)).getContent());
        return "admin/departments";
    }

    @PostMapping("/departments/add")
    public String addDepartment(@RequestParam String name,
                                @RequestParam String address,
                                @RequestParam(required = false) String phone,
                                @RequestParam(required = false) String email,
                                RedirectAttributes ra) {
        DepartmentModel d = new DepartmentModel();
        d.setName(name);
        d.setAddress(address);
        d.setPhone(phone);
        d.setEmail(email);
        departmentService.save(d);
        ra.addFlashAttribute("successMessage", "Отделение добавлено");
        return "redirect:/admin/departments";
    }

    @PostMapping("/departments/delete")
    public String deleteDepartment(@RequestParam Long id, RedirectAttributes ra) {
        departmentService.deleteById(id);
        ra.addFlashAttribute("successMessage", "Отделение удалено");
        return "redirect:/admin/departments";
    }
}