package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Class;
import com.kachunchan.academicrecordbook.domain.Subject;
import com.kachunchan.academicrecordbook.repository.ClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClassServiceImpTest {

    private ClassService ClassService;
    @Mock
    private ClassRepository ClassRepository;

    @BeforeEach
    public void setup() {
        ClassService = new ClassServiceImpl(ClassRepository);
    }

    @Test
    public void givenANewClass_whenAddClassHasBeenInvoked_thenAddClassToRepositoryAndReturnClass() {
        Class newClass = new Class("MM1010202101", new Subject("MM101", "Mathematics Level 1"));
        when(ClassRepository.existsById(anyString())).thenReturn(false);
        when(ClassRepository.save(any(Class.class))).thenReturn(newClass);

        Class expectedClass = newClass;
        Class actualClass = ClassService.addClass(newClass);

        assertEquals(expectedClass, actualClass);
        verify(ClassRepository).existsById("MM1010202101");
        verify(ClassRepository).save(newClass);
    }

    @Test
    public void givenAnExistingClass_whenAddClassHasBeenInvoked_thenDoNotAddClassToRepositoryAndReturnNull() {
        Class newClass = new Class("MM1010202101", new Subject("MM101", "Mathematics Level 1"));
        when(ClassRepository.existsById(anyString())).thenReturn(true);

        Class actualClass = ClassService.addClass(newClass);

        assertNull(actualClass);
        verify(ClassRepository).existsById("MM1010202101");
        verifyNoMoreInteractions(ClassRepository);
    }

    @Test
    public void givenAnExistingClassCode_whenGetClassHasBeenInvoked_thenRetrieveAndReturnClassFromRepository() {
        String code = "MM1010202101";
        Class stubbedClass = new Class("MM1010202101", new Subject("MM101", "Mathematics Level 1"));
        when(ClassRepository.existsById(anyString())).thenReturn(true);
        when(ClassRepository.getOne(anyString())).thenReturn(stubbedClass);

        Class expectedClass = stubbedClass;
        Class actualClass = ClassService.getClass(code);

        assertEquals(expectedClass, actualClass);
        verify(ClassRepository).existsById("MM1010202101");
        verify(ClassRepository).getOne("MM1010202101");
    }

    @Test
    public void givenAnNonExistingClassCode_whenGetClassHasBeenInvoked_thenDoNotRetrieveFromRepositoryAndReturnNull() {
        String code = "MM1010202101";
        when(ClassRepository.existsById(anyString())).thenReturn(false);

        Class actualClass = ClassService.getClass(code);

        assertNull(actualClass);
        verify(ClassRepository).existsById("MM1010202101");
        verifyNoMoreInteractions(ClassRepository);
    }

    @Test
    public void givenAllClassAreToBeRetrieved_whenGetAllClassHasBeenInvoked_thenRetrieveAllClassAsAListAndReturnTheList() {
        List<Class> Class = new ArrayList<>();
        Class.add(new Class("MM1010202101", new Subject("MM101", "Mathematics Level 1")));
        Class.add(new Class("MM1010202102", new Subject("MM101", "Mathematics Level 1")));
        Class.add(new Class("MM1010202103", new Subject("MM101", "Mathematics Level 1")));
        when(ClassRepository.findAll()).thenReturn(Class);

        int expectedNumberOfClass = 3;
        int actualNumberOfClass = ClassService.getAllClass().size();

        assertEquals(expectedNumberOfClass, actualNumberOfClass);
        verify(ClassRepository).findAll();
    }

    @Test
    public void givenAClassThatHasBeenEdited_whenEditClassHasBeenInvoked_thenSaveClassIntoRepositoryAndReturnClass() {
        Class editedClass = new Class("MM1010202101", new Subject("MM101", "Mathematics Level 1"));
        when(ClassRepository.existsById(anyString())).thenReturn(true);
        when(ClassRepository.save(any(Class.class))).thenReturn(editedClass);

        Class expectedClass = editedClass;
        Class actualClass = ClassService.editClass(editedClass);

        assertEquals(expectedClass, actualClass);
        verify(ClassRepository).existsById("MM1010202101");
        verify(ClassRepository).save(editedClass);
    }

    @Test
    public void givenTwoEndUserEditingTheSameClassAndOneHasCommittedTheChange_whenEditClassHasBeenInvokedByTheOtherEndUser_thenDoNotSaveClassInToRepositoryAndReturnNull() {
        Class editedClass = new Class("MM1010202101", new Subject("MM101", "Mathematics Level 1"));
        when(ClassRepository.existsById(anyString())).thenReturn(false);

        Class actualClass = ClassService.editClass(editedClass);

        assertNull(actualClass);
        verify(ClassRepository).existsById("MM1010202101");
        verifyNoMoreInteractions(ClassRepository);
    }

    @Test
    public void givenAClassToBeDeletedByIdCode_whenDeleteClassHasBeenInvoked_thenDeleteClassFromRepository() {
        String code = "MM1010202101";

        ClassService.deleteClass(code);

        verify(ClassRepository).deleteById(code);
    }
}
