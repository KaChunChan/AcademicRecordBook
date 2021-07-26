package com.kachunchan.academicrecordbook.controller;

import com.kachunchan.academicrecordbook.domain.*;
import com.kachunchan.academicrecordbook.service.EndUserFormService;
import com.kachunchan.academicrecordbook.service.EndUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
public class AdminController {

    private final EndUserService endUserService;
    private final EndUserFormService endUserFormService;
    private final List<Role> availableRoles;

    public AdminController(EndUserService endUserService, EndUserFormService endUserFormService) {
        this.endUserService = endUserService;
        this.endUserFormService = endUserFormService;
        this.availableRoles = Arrays.asList(Role.values());
    }

    @GetMapping("/admin")
    public String getAdminPage(@ModelAttribute("error") String error, Model model) {
        List<EndUser> endUsers = endUserService.getAllEndUsers();
        model.addAttribute("endUsers", endUsers);
        return "admin";
    }

    @GetMapping("/admin-add-user")
    public String getAdminAddUserPage(@ModelAttribute("endUser") EndUserForm endUserForm, Model model) {
        model.addAttribute("availableRoles", availableRoles);
        return "admin-account-form";
    }

    @PostMapping("/admin-add-user")
    public String addNewUser(@Valid @ModelAttribute("endUser") EndUserForm endUserForm, BindingResult bindingResult, Model model) {
        model.addAttribute("availableRoles", availableRoles);
        if(bindingResult.hasErrors()) {
            return "admin-account-form";
        }
        if (endUserService.addEndUser(endUserFormService.convertToEndUser(endUserForm)) == null) {
            bindingResult.addError(new ObjectError("username", "Username already exists."));
            return "admin-account-form";
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin-delete-user")
    public String deleteUser(@RequestParam("endUserID") String accountID, RedirectAttributes redirectAttributes) {
        long id = Long.parseLong(accountID);
        if (endUserService.getEndUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId() == id) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete current user.");
            return "redirect:/admin";
        }
        if (endUserService.getEndUser(id) == null) {
            redirectAttributes.addFlashAttribute("error", "User does not exist or has already been deleted.");
            return "redirect:/admin";
        }
        endUserService.deleteEndUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin-edit-user")
    public String editUser(@RequestParam("endUserID") String endUserID, Model model) {
        long id = Long.parseLong(endUserID);
        EndUserForm endUserForm = endUserFormService.convertToEndUserForm(endUserService.getEndUser(id));
        model.addAttribute("endUserID", endUserID);
        model.addAttribute("endUser", endUserForm);
        model.addAttribute("availableRoles", availableRoles);
        return "admin-account-form";
    }

    @PostMapping("/admin-edit-user")
    public String updateUser(@RequestParam("endUserID") String endUserID, @Valid @ModelAttribute("endUser") EndUserForm endUserForm, BindingResult bindingResult, Model model) {
        long id = Long.parseLong(endUserID);
        model.addAttribute("endUserID", id);
        model.addAttribute("availableRoles", availableRoles);
        if (bindingResult.hasErrors()) {
            return "admin-account-form";
        }

        EndUser existingEndUser = endUserService.getEndUser(endUserForm.getUsername());
        if (existingEndUser != null && existingEndUser.getId() != id) {
            return "admin-account-form";
        }

        EndUser editedEndUser = endUserFormService.convertToEndUser(endUserForm);
        editedEndUser.setId(id);
        endUserService.editEndUser(editedEndUser);
        return "redirect:/admin";
    }
}
