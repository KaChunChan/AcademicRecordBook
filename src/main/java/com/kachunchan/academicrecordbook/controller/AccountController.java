package com.kachunchan.academicrecordbook.controller;

import com.kachunchan.academicrecordbook.service.EndUserService;
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

    private final EndUserService endUserService;

    public AccountController(EndUserService endUserService) {
        this.endUserService = endUserService;
    }

    @RequestMapping(value = "/account", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAccountView(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new SessionAuthenticationException("Session user is null");
        }
        model.addAttribute("endUser", endUserService.getEndUser(authentication.getName()));
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
