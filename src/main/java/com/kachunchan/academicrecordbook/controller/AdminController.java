package com.kachunchan.academicrecordbook.controller;

import com.kachunchan.academicrecordbook.domain.Account;
import com.kachunchan.academicrecordbook.domain.AccountForm;
import com.kachunchan.academicrecordbook.domain.Role;
import com.kachunchan.academicrecordbook.service.AccountFormService;
import com.kachunchan.academicrecordbook.service.AccountService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
public class AdminController {

    private final AccountService accountService;
    private final AccountFormService accountFormService;
    private final List<Role> availableRoles;

    public AdminController(AccountService accountService, AccountFormService accountFormService) {
        this.accountService = accountService;
        this.accountFormService = accountFormService;
        this.availableRoles = Arrays.asList(Role.values());
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdminPage(@ModelAttribute("error") String error, Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "admin";
    }

    @RequestMapping(value = "/admin-add-user", method = RequestMethod.GET)
    public String getAdminAddUserPage(@ModelAttribute("account") AccountForm accountForm, Model model) {
        model.addAttribute("availableRoles", availableRoles);
        return "admin-account-form";
    }

    @RequestMapping(value = "/admin-add-user", method = RequestMethod.POST)
    public String addNewUser(@Valid @ModelAttribute("account") AccountForm accountForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || accountService.getAnAccount(accountForm.getUsername()) != null) {
            model.addAttribute("availableRoles", availableRoles);
            return "admin-account-form";
        }
        accountService.addAccount(accountFormService.makeIntoAccount(accountForm));
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin-delete-user", method = RequestMethod.POST)
    public String deleteUser(@RequestParam("accountID") String accountID, RedirectAttributes redirectAttributes) {
        long id = Long.parseLong(accountID);
        if (accountService.getAnAccount(SecurityContextHolder.getContext().getAuthentication().getName()).getId() == id) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete current user account");
            return "redirect:/admin";
        }
        if (accountService.getAnAccount(id) == null) {
            redirectAttributes.addFlashAttribute("error", "Account does not exist or has already been deleted");
            return "redirect:/admin";
        }
        accountService.deleteAccount(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin-edit-user", method = RequestMethod.GET)
    public String editUser(@RequestParam("accountID") String accountID, Model model) {
        long id = Long.parseLong(accountID);
        Account account = accountService.getAnAccount(id);
        AccountForm accountForm = accountFormService.makeIntoAccountForm(account);
        model.addAttribute("accountID", accountID);
        model.addAttribute("account", accountForm);
        model.addAttribute("availableRoles", availableRoles);
        return "admin-account-form";
    }

    @RequestMapping(value = "/admin-edit-user", method = RequestMethod.POST)
    public String updateUser(@RequestParam("accountID") String accountID, @Valid @ModelAttribute("account") AccountForm accountForm, BindingResult bindingResult, Model model) {
        long id = Long.parseLong(accountID);
        model.addAttribute("accountID", id);
        model.addAttribute("availableRoles", availableRoles);
        if (bindingResult.hasErrors()) {
            return "admin-account-form";
        }

        Account existingAccount = accountService.getAnAccount(accountForm.getUsername());
        if (existingAccount != null && existingAccount.getId() != id) {
            return "admin-account-form";
        }

        Account editedAccount = accountFormService.makeIntoAccount(accountForm);
        editedAccount.setId(id);
        accountService.updateAccount(editedAccount);
        return "redirect:/admin";
    }
}
