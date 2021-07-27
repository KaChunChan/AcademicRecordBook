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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getAdminClassesPage(@ModelAttribute("classForm") ClassForm classForm, @ModelAttribute("error") String error, Model model) {
        List<Class> classes = classService.getAllClass();
        List<Subject> subjects = subjectService.getAllSubject();
        model.addAttribute("classes", classes);
        model.addAttribute("subjects", subjects);
        model.addAttribute("error", error);
        return "admin-classes";
    }

    @PostMapping("/admin-classes-add-class")
    public String addNewClass(@Valid @ModelAttribute("classForm") ClassForm classForm, BindingResult bindingResult, Model model) {
        List<Class> classes = classService.getAllClass();
        List<Subject> subjects = subjectService.getAllSubject();
        model.addAttribute("classes", classes);
        model.addAttribute("subjects", subjects);
        if (bindingResult.hasErrors()) {
            return "admin-classes";
        }
        if (classService.getClass(classForm.getCode()) != null) {
            model.addAttribute("error", "Class already exists.");
            return "admin-classes";
        }
        classService.addClass(classFormService.convertToClass(classForm));
        return "redirect:/admin-classes";
    }

    @GetMapping("/admin-view-class")
    public String viewClass(@RequestParam("code") String code, Model model, RedirectAttributes redirectAttributes) {
        Class classs = classService.getClass(code);
        if (classs == null) {
            String errorMessage = "Class has been changed or deleted.";
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin-classes";
        }
        model.addAttribute("class", classs);
        return "admin-view-class";
    }

    @GetMapping("/admin-edit-class")
    public String editClass(@RequestParam("code") String code, Model model, RedirectAttributes redirectAttributes) {
        Class classs = classService.getClass(code);
        if (classs == null) {
            String errorMessage = "Class has been changed or deleted.";
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin-classes";
        }
        ClassForm classForm = classFormService.convertToClassForm(classs);
        model.addAttribute("classForm", classForm);
        return "admin-edit-class";
    }

    @PostMapping("/admin-update-class")
    public String updateClass(@Valid @ModelAttribute("classForm") ClassForm classForm, @RequestParam("classCode") String classCode, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin-edit-class";
        }
        Class existingClass = classService.getClass(classForm.getCode());
        if (existingClass != null && !existingClass.getCode().equals(classCode)) {
            String errorMessage = "Class ID Code already exists.";
            model.addAttribute("error", errorMessage);
            return "admin-edit-class";
        }
        if (classForm.getCode().equals(classCode)) {
            classService.editClass(classFormService.convertToClass(classForm));
        } else {
            classService.addClass(classFormService.convertToClass(classForm));
            classService.deleteClass(classCode);
        }
        return "redirect:/admin-classes";
    }
}
