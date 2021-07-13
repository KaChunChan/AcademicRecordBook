package com.kachunchan.academicrecordbook.controller;

import com.kachunchan.academicrecordbook.domain.Account;
import com.kachunchan.academicrecordbook.domain.AccountForm;
import com.kachunchan.academicrecordbook.domain.Role;
import com.kachunchan.academicrecordbook.service.AccountFormService;
import com.kachunchan.academicrecordbook.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String getAdminPage(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        System.out.println(model.toString());
        return "admin";
    }

    @RequestMapping(value = "/admin-add-user", method = RequestMethod.GET)
    public String getAdminAddUserPage(@ModelAttribute("account") AccountForm account, Model model) {
        model.addAttribute("availableRoles", availableRoles);
        return "admin-add-user";
    }

    @RequestMapping(value = "/admin-add-user", method = RequestMethod.POST)
    public String addNewUser(@Valid @ModelAttribute("account") AccountForm account, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || accountService.getAnAccount(account.getUsername()) != null) {
            model.addAttribute("availableRoles", availableRoles);
            return "admin-add-user";
        }
        accountService.addAccount(accountFormService.makeIntoAccount(account));
        return "redirect:/admin";
    }
}
