package com.kachunchan.academicrecordbook.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = {"/academicrecordbook", "/"}, method = RequestMethod.GET)
    public String showLoginPageForHomePage() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
                return "redirect:/account";
            }
        }
        return "redirect:/login";
    }
}