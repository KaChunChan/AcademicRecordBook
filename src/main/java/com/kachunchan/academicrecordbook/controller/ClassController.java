package com.kachunchan.academicrecordbook.controller;

import com.kachunchan.academicrecordbook.domain.Class;
import com.kachunchan.academicrecordbook.domain.ClassForm;
import com.kachunchan.academicrecordbook.domain.Subject;
import com.kachunchan.academicrecordbook.service.ClassFormService;
import com.kachunchan.academicrecordbook.service.ClassService;
import com.kachunchan.academicrecordbook.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ClassController {

    private final ClassFormService classFormService;
    private final ClassService classService;
    private final SubjectService subjectService;

    public ClassController(ClassFormService classFormService, ClassService classService, SubjectService subjectService) {
        this.classFormService = classFormService;
        this.classService = classService;
        this.subjectService = subjectService;
    }

    @GetMapping("/admin-classes")
    public String getAdminClassesPage(@ModelAttribute("classForm") ClassForm classForm, Model model) {
        List<Class> classes = classService.getAllClass();
        List<Subject> subjects = subjectService.getAllSubject();
        model.addAttribute("classes", classes);
        model.addAttribute("subjects", subjects);
        return "admin-classes";
    }

    @PostMapping("/admin-classes-add-class")
    public String addNewClass(@Valid @ModelAttribute("classForm") ClassForm classForm, BindingResult bindingResult, Model model) {
        List<Class> classes = classService.getAllClass();
        List<Subject> subjects = subjectService.getAllSubject();
        model.addAttribute("classes", classes);
        model.addAttribute("subjects", subjects);
        if(bindingResult.hasErrors()) {
            return "admin-classes";
        }
        if (classService.getClass(classForm.getCode()) != null) {
            model.addAttribute("error", "Class already exists.");
            return "admin-classes";
        }
        classService.addClass(classFormService.convertToClass(classForm));
        return "redirect:/admin-classes";
    }
}
