package com.kachunchan.academicrecordbook.controller;

import com.kachunchan.academicrecordbook.domain.Class;
import com.kachunchan.academicrecordbook.domain.ClassForm;
import com.kachunchan.academicrecordbook.domain.Subject;
import com.kachunchan.academicrecordbook.service.ClassFormService;
import com.kachunchan.academicrecordbook.service.ClassService;
import com.kachunchan.academicrecordbook.service.SubjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClassController.class)
@TestPropertySource(locations = "classpath:test.properties")
public class ClassControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private ClassFormService classFormService;
    @MockBean
    private SubjectService subjectService;
    @MockBean
    private ClassService classService;
    @Value("${spring.mvc.view.prefix}")
    private String prefixView;

    //Test getAdminClassesPage()

    @Test
    @WithMockUser(authorities = "A")
    public void givenAdminEndUser_whenRequestingClassesPage_thenReturnClassesViewWithAListOfExistingClassesAndAListOfAllExistingSubjects() throws Exception {
        RequestBuilder request = get("/admin-classes").with(csrf());

        Subject subject1 = new Subject("MM101", "Mathematics Level 1");
        Subject subject2 = new Subject("MM201", "Mathematics Level 2");
        Subject subject3 = new Subject("MM301", "Mathematics Level 3");
        List<Subject> subjects = new ArrayList<>();
        subjects.add(subject1);
        subjects.add(subject2);
        subjects.add(subject3);
        Class class1 = new Class("MM1010202001", subject1);
        Class class2 = new Class("MM1010202002", subject2);
        Class class3 = new Class("MM1010202003", subject3);
        List<Class> classes = new ArrayList<>();
        classes.add(class1);
        classes.add(class2);
        classes.add(class3);
        when(classService.getAllClass()).thenReturn(classes);
        when(subjectService.getAllSubject()).thenReturn(subjects);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView + "admin-classes.jsp"))
                .andExpect(view().name("admin-classes"))
                .andExpect(model().attribute("classes", hasItems(class1)))
                .andExpect(model().attribute("classes", hasItems(class2)))
                .andExpect(model().attribute("classes", hasItems(class3)))
                .andExpect(model().attribute("subjects", hasItems(subject1)))
                .andExpect(model().attribute("subjects", hasItems(subject2)))
                .andExpect(model().attribute("subjects", hasItems(subject3)));
        verify(classService).getAllClass();
        verify(subjectService).getAllSubject();
    }

    //Test addNewClass()

    @Test
    @WithMockUser(authorities = "A")
    public void givenAdminEndUserAddingNewNonExistingClass_whenRequestingAddNewClass_thenAddClassAndRedirectToAdminClassesPage() throws Exception {
        RequestBuilder request = post("/admin-classes-add-class")
                .param("code", "MM1010202001")
                .param("subject.code", "MM101")
                .param("subject.title", "Mathematics Level 1")
                .with(csrf());

        Subject subject = new Subject("MM1010202001", "Mathematics Level 1");
        Class stubbedClass = new Class("MM1010202001", subject);
        when(classService.getClass(anyString())).thenReturn(null);
        when(classFormService.convertToClass(any())).thenReturn(stubbedClass);
        when(classService.addClass(any(Class.class))).thenReturn(stubbedClass);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-classes"));
        verify(classService).getClass("MM1010202001");
        verify(classFormService).convertToClass(any(ClassForm.class));
        verify(classService).addClass(stubbedClass);
    }

    @Test
    @WithMockUser(authorities = "A")
    public void givenAdminEndUserAddingClassThatAlreadyExist_whenRequestingAddNewClass_thenDoNotAddClassAndRedirectToAdminClassesPageWithErrorMessage() throws Exception {
        RequestBuilder request = post("/admin-classes-add-class")
                .param("code", "MM1010202001")
                .param("subject.code", "MM101")
                .param("subject.title", "Mathematics Level 1")
                .with(csrf());

        Subject subject1 = new Subject("MM101", "Mathematics Level 1");
        Subject subject2 = new Subject("MM201", "Mathematics Level 2");
        Subject subject3 = new Subject("MM301", "Mathematics Level 3");
        List<Subject> subjects = new ArrayList<>();
        subjects.add(subject1);
        subjects.add(subject2);
        subjects.add(subject3);
        Class class1 = new Class("MM1010202001", subject1);
        Class class2 = new Class("MM1010202002", subject2);
        Class class3 = new Class("MM1010202003", subject3);
        List<Class> classes = new ArrayList<>();
        classes.add(class1);
        classes.add(class2);
        classes.add(class3);
        when(subjectService.getAllSubject()).thenReturn(subjects);
        when(classService.getAllClass()).thenReturn(classes);
        when(classService.getClass(anyString())).thenReturn(new Class());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView + "admin-classes.jsp"))
                .andExpect(view().name("admin-classes"))
                .andExpect(model().attribute("error", "Class already exists."))
                .andExpect(model().attribute("classes", hasItems(class1)))
                .andExpect(model().attribute("classes", hasItems(class2)))
                .andExpect(model().attribute("classes", hasItems(class3)))
                .andExpect(model().attribute("subjects", hasItems(subject1)))
                .andExpect(model().attribute("subjects", hasItems(subject2)))
                .andExpect(model().attribute("subjects", hasItems(subject3)));
        verify(classService).getAllClass();
        verify(classService).getClass("MM1010202001");
        verifyNoMoreInteractions(classService);
        verifyNoInteractions(classFormService);
        verify(subjectService).getAllSubject();
    }

    //Test viewClass()

    @Test
    @WithMockUser(authorities = "A")
    public void givenAdminEndUserSelectedAClassToView_whenRequestingViewClassPage_thenReturnAdminClassViewWithTheClassDetails() throws Exception {
        RequestBuilder request = get("/admin-view-class")
                .param("code", "MM1010202101")
                .with(csrf());
        Subject stubbedSubject = new Subject("MM101", "Mathematics Level 1");
        Class stubbedClass = new Class("MM1010202101", stubbedSubject);
        when(classService.getClass(anyString())).thenReturn(stubbedClass);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView + "admin-view-class.jsp"))
                .andExpect(view().name("admin-view-class"))
                .andExpect(model().attribute("class", hasProperty("code", is("MM1010202101"))))
                .andExpect(model().attribute("class", hasProperty("subject", hasProperty("code", is("MM101")))))
                .andExpect(model().attribute("class", hasProperty("subject", hasProperty("title", is("Mathematics Level 1")))));
        verify(classService).getClass("MM1010202101");
    }

    @Test
    @WithMockUser(authorities = "A")
    public void givenAdminEndUserSelectedAClassToViewButHasBeenSimultaneouslyDeletedOrModified_whenRequestingViewClassPage_thenReturnToAdminClassPageWithError() throws Exception {
        RequestBuilder request = get("/admin-view-class")
                .param("code", "MM1010202101")
                .with(csrf());
        when(classService.getClass(anyString())).thenReturn(null);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-classes"))
                .andExpect(flash().attribute("error", "Class has been changed or deleted."));
        verify(classService).getClass("MM1010202101");
    }

    @Test
    @WithMockUser(authorities = "A")
    public void givenAdminEndUserSelectedAClassToViewButHasBeenSimultaneouslyDeletedOrModified_whenRedirectedToAdminClassPage_thenReturnTheUsualClassesViewWithErrorMessage() throws Exception {
        RequestBuilder request = get("/admin-classes")
                .flashAttr("error", "Class has been changed or deleted.")
                .with(csrf());

        Subject subject1 = new Subject("MM101", "Mathematics Level 1");
        Subject subject2 = new Subject("MM201", "Mathematics Level 2");
        Subject subject3 = new Subject("MM301", "Mathematics Level 3");
        List<Subject> subjects = new ArrayList<>();
        subjects.add(subject1);
        subjects.add(subject2);
        subjects.add(subject3);
        Class class1 = new Class("MM1010202001", subject1);
        Class class2 = new Class("MM1010202002", subject2);
        Class class3 = new Class("MM1010202003", subject3);
        List<Class> classes = new ArrayList<>();
        classes.add(class1);
        classes.add(class2);
        classes.add(class3);
        when(classService.getAllClass()).thenReturn(classes);
        when(subjectService.getAllSubject()).thenReturn(subjects);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView + "admin-classes.jsp"))
                .andExpect(view().name("admin-classes"))
                .andExpect(model().attribute("classes", hasItems(class1)))
                .andExpect(model().attribute("classes", hasItems(class2)))
                .andExpect(model().attribute("classes", hasItems(class3)))
                .andExpect(model().attribute("subjects", hasItems(subject1)))
                .andExpect(model().attribute("subjects", hasItems(subject2)))
                .andExpect(model().attribute("subjects", hasItems(subject3)))
                .andExpect(model().attribute("error", "Class has been changed or deleted."));
        verify(classService).getAllClass();
        verify(subjectService).getAllSubject();
    }
}
