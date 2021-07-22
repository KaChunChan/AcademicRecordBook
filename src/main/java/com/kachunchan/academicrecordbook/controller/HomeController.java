package com.kachunchan.academicrecordbook.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/academicrecordbook", "/"})
    public String redirectToLogin() {
                if (SecurityContextHolder.getContext().getAuthentication() != null) {
            if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
                return "redirect:/account";
            }
        }
        return "redirect:/login";
    }
}