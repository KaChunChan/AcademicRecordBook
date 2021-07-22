package com.kachunchan.academicrecordbook.controller;

import com.kachunchan.academicrecordbook.domain.Subject;
import com.kachunchan.academicrecordbook.domain.SubjectForm;
import com.kachunchan.academicrecordbook.service.SubjectFormService;
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
public class SubjectController {

    private SubjectService subjectService;
    private SubjectFormService subjectFormService;

    public SubjectController(SubjectService subjectService, SubjectFormService subjectFormService) {
        this.subjectService = subjectService;
        this.subjectFormService = subjectFormService;
    }

    @GetMapping("/admin-subjects")
    public String getAdminSubjectsPage(@ModelAttribute("subjectForm") SubjectForm subjectForm, Model model) {
        List<Subject> subjects = subjectService.getAllSubject();
        model.addAttribute("subjects", subjects);
        return "admin-subjects";
    }

    @ModelAttribute("subjectForm")
    public SubjectForm subjectFormObject() {
        return new SubjectForm();
    }

    @PostMapping("/admin-subjects-add-subject")
    public String addNewSubject(@Valid @ModelAttribute("subjectForm") SubjectForm subjectForm, BindingResult bindingResult, Model model) {
        List<Subject> subjects = subjectService.getAllSubject();
        model.addAttribute("subjects", subjects);
        if(bindingResult.hasErrors()) {
            return "admin-subjects";
        }
        if (subjectService.getSubject(subjectForm.getCode()) != null) {
            model.addAttribute("error", "Subject already exists.");
            return "admin-subjects";
        }
        subjectService.addSubject(subjectFormService.convertToSubject(subjectForm));
        return "redirect:/admin-subjects";
    }
}
