package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Class;
import com.kachunchan.academicrecordbook.domain.ClassForm;
import com.kachunchan.academicrecordbook.domain.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ClassFromServiceImplTest {

    private ClassFormService ClassFormService;

    @BeforeEach
    public void setup() {
        ClassFormService = new ClassFormServiceImpl();
    }

    @Test
    public void givenAClassForm_whenConvertToClassHasBeenInvoked_thenReturnClass() {
        Subject subject = new Subject("MM101", "Mathematics Level 1");
        ClassForm ClassForm = new ClassForm();
        ClassForm.setCode("MM1010202101");
        ClassForm.setSubject(subject);

        Class expectedClass = new Class("MM1010202101", subject);
        Class actualClass = ClassFormService.convertToClass(ClassForm);

        assertEquals(expectedClass, actualClass);
    }

    @Test
    public void givenAClass_whenConvertToClassFormHasBeenInvoked_thenReturnClassForm() {
        Subject subject = new Subject("MM101", "Mathematics Level 1");
        Class Class = new Class("MM101", subject);

        ClassForm expectedClassFrom = new ClassForm();
        expectedClassFrom.setCode("MM101");
        expectedClassFrom.setSubject(subject);
        ClassForm actualClassForm = ClassFormService.convertToClassForm(Class);

        assertEquals(expectedClassFrom, actualClassForm);
    }
}
