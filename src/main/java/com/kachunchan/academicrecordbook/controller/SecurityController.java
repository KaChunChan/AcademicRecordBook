package com.kachunchan.academicrecordbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String login(@RequestParam(value="error", required = false) String error,
                        @RequestParam(value="logout", required = false) String logout,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        return "login";
    }
}
