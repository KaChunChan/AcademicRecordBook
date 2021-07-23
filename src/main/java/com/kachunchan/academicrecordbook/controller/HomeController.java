package com.kachunchan.academicrecordbook.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
//    @Autowired
//    private EndUserService endUserService;

    @GetMapping(value = {"/academicrecordbook", "/"})
    public String redirectToLogin() {

//        runOnce();

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
                return "redirect:/account";
            }
        }
        return "redirect:/login";
    }

//    private void runOnce() {
//
//        EndUser admin = new Admin("Ad", "Min", "admin", "admin@email.com", "password");
//        EndUser instructor = new Instructor("Inst", "Ructor", "instructor", "instructor@email.com", "password");
//        EndUser student = new Student("Stu", "Dent", "student", "student@email.com", "password");
//
//        endUserService.addEndUser(admin);
//        endUserService.addEndUser(instructor);
//        endUserService.addEndUser(student);
//
//    }
}