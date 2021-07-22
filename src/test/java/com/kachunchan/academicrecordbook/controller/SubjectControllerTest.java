package com.kachunchan.academicrecordbook.controller;

import com.kachunchan.academicrecordbook.domain.Subject;
import com.kachunchan.academicrecordbook.domain.SubjectForm;
import com.kachunchan.academicrecordbook.service.SubjectFormService;
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

@WebMvcTest(SubjectController.class)
@TestPropertySource(locations = "classpath:test.properties")
public class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private SubjectService subjectService;
    @MockBean
    private SubjectFormService subjectFormService;
    @Value("${spring.mvc.view.prefix}")
    private String prefixView;

    //Test getAdminSubjectsPage

    @Test
    @WithMockUser(authorities = "A")
    public void givenAdminEndUser_whenRequestingSubjectPage_thenReturnSubjectViewWithAllTheAvailableSubjects() throws Exception {
        RequestBuilder request = get("/admin-subjects")
                .with(csrf());

        Subject subject1 = new Subject("MM101", "Mathematics Level 1");
        Subject subject2 = new Subject("MM201", "Mathematics Level 2");
        Subject subject3 = new Subject("MM301", "Mathematics Level 3");
        List<Subject> subjects = new ArrayList<>();
        subjects.add(subject1);
        subjects.add(subject2);
        subjects.add(subject3);
        when(subjectService.getAllSubject()).thenReturn(subjects);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView + "admin-subjects.jsp"))
                .andExpect(view().name("admin-subjects"))
                .andExpect(model().attribute("subjects", hasItems(subject1)))
                .andExpect(model().attribute("subjects", hasItems(subject2)))
                .andExpect(model().attribute("subjects", hasItems(subject3)));
        verify(subjectService).getAllSubject();
    }

    //Test addNewSubject()

    @Test
    @WithMockUser(authorities = "A")
    public void givenAdminEndUserAddingNewNonExistingSubject_whenRequestingAddNewSubject_thenAddSubjectAndRedirectToAdminSubjectsPage() throws Exception {
        RequestBuilder request = post("/admin-subjects-add-subject")
                .param("code", "MM101")
                .param("title", "Mathematics Level 1")
                .with(csrf());

        Subject stubbedSubject = new Subject("MM101", "Mathematics Level 1");
        when(subjectService.getSubject(anyString())).thenReturn(null);
        when(subjectFormService.convertToSubject(any())).thenReturn(stubbedSubject);
        when(subjectService.addSubject(any(Subject.class))).thenReturn(stubbedSubject);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-subjects"));
        verify(subjectService).getSubject("MM101");
        verify(subjectFormService).convertToSubject(any(SubjectForm.class));
        verify(subjectService).addSubject(stubbedSubject);
    }

    @Test
    @WithMockUser(authorities = "A")
    public void givenAdminEndUserAddingSubjectThatAlreadyExist_whenRequestingAddNewSubject_thenDoNotAddSubjectAndRedirectToAdminSubjectsPageWithErrorMessage() throws Exception {
        RequestBuilder request = post("/admin-subjects-add-subject")
                .param("code", "MM101")
                .param("title", "Mathematics Level 1")
                .with(csrf());

        Subject subject1 = new Subject("MM101", "Mathematics Level 1");
        Subject subject2 = new Subject("MM201", "Mathematics Level 2");
        Subject subject3 = new Subject("MM301", "Mathematics Level 3");
        List<Subject> subjects = new ArrayList<>();
        subjects.add(subject1);
        subjects.add(subject2);
        subjects.add(subject3);
        when(subjectService.getAllSubject()).thenReturn(subjects);
        when(subjectService.getSubject(anyString())).thenReturn(new Subject());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView + "admin-subjects.jsp"))
                .andExpect(view().name("admin-subjects"))
                .andExpect(model().attribute("subjects", hasItems(subject1)))
                .andExpect(model().attribute("subjects", hasItems(subject2)))
                .andExpect(model().attribute("subjects", hasItems(subject3)));
        verify(subjectService).getAllSubject();
        verify(subjectService).getSubject("MM101");
        verifyNoMoreInteractions(subjectService);
        verifyNoInteractions(subjectFormService);
    }
}
