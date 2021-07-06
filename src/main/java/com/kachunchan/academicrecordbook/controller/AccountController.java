package com.kachunchan.academicrecordbook.controller;

import com.kachunchan.academicrecordbook.service.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @RequestMapping(value = "/account", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAccountPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new SessionAuthenticationException("Session user is null");
        }
        model.addAttribute("account", accountService.getAnAccount(authentication.getName()));
        return "account";
    }

    @ExceptionHandler(SessionAuthenticationException.class)
    public String handleSessionAuthenticationException(HttpServletRequest request, Exception exception) {
        return "redirect:/login";
    }

    @RequestMapping(value = "/to-account", method = {RequestMethod.GET, RequestMethod.POST})
    public String redirectToAccountPage() {
        return "redirect:/account";
    }
}
