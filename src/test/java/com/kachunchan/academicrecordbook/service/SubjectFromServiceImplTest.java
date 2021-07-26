package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Subject;
import com.kachunchan.academicrecordbook.domain.SubjectForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SubjectFromServiceImplTest {

    private SubjectFormService subjectFormService;

    @BeforeEach
    public void setup() {
        subjectFormService = new SubjectFormServiceImpl();
    }

    @Test
    public void givenASubjectFrom_whenConvertToSubjectHasBeenInvoked_thenReturnSubject() {
        SubjectForm subjectForm = new SubjectForm();
        subjectForm.setCode("MM101");
        subjectForm.setTitle("Mathematics Level 1");

        Subject expectedSubject = new Subject("MM101", "Mathematics Level 1");
        Subject actualSubject = subjectFormService.convertToSubject(subjectForm);

        assertEquals(expectedSubject, actualSubject);
    }

    @Test
    public void givenASubject_whenConvertToSubjectFormHasBeenInvoked_thenReturnSubjectForm() {
        Subject subject = new Subject("MM101", "Mathematics Level 1");

        SubjectForm expectedSubjectForm = new SubjectForm();
        expectedSubjectForm.setCode("MM101");
        expectedSubjectForm.setTitle("Mathematics Level 1");
        SubjectForm actualSubjectForm = subjectFormService.convertToSubjectForm(subject);

        assertEquals(expectedSubjectForm, actualSubjectForm);
    }
}
