package com.kachunchan.academicrecordbook.account.controller;

import com.kachunchan.academicrecordbook.account.exception.AccountDoesNotExistException;
import com.kachunchan.academicrecordbook.account.service.AccountService;
import com.kachunchan.academicrecordbook.account.domain.Account;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/{username}/account", method = RequestMethod.GET)
    public String getAnAccount(@PathVariable String username, ModelMap model) {
        Account account = accountService.getAnAccount(username);
        model.addAttribute("account", account);
        System.out.println("The model has: " + model.toString());
        return "account";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void accountNotFoundException(AccountDoesNotExistException ex) {
    }
}
