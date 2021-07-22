package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Subject;
import com.kachunchan.academicrecordbook.repository.SubjectRepository;
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
public class SubjectServiceImpTest {

    private SubjectService subjectService;
    @Mock
    private SubjectRepository subjectRepository;

    @BeforeEach
    public void setup() {
        subjectService = new SubjectServiceImpl(subjectRepository);
    }

    @Test
    public void givenANewSubject_whenAddSubjectHasBeenInvoked_thenAddSubjectToRepositoryAndReturnSubject() {
        Subject newSubject = new Subject("MM101", "Mathematics Level 1");
        when(subjectRepository.existsById(anyString())).thenReturn(false);
        when(subjectRepository.save(any(Subject.class))).thenReturn(newSubject);

        Subject expectedSubject = newSubject;
        Subject actualSubject = subjectService.addSubject(newSubject);

        assertEquals(expectedSubject, actualSubject);
        verify(subjectRepository).existsById("MM101");
        verify(subjectRepository).save(newSubject);
    }

    @Test
    public void givenAnExistingSubject_whenAddSubjectHasBeenInvoked_thenDoNotAddSubjectToRepositoryAndReturnNull() {
        Subject newSubject = new Subject("MM101", "Mathematics Level 1");
        when(subjectRepository.existsById(anyString())).thenReturn(true);

        Subject actualSubject = subjectService.addSubject(newSubject);

        assertNull(actualSubject);
        verify(subjectRepository).existsById("MM101");
        verifyNoMoreInteractions(subjectRepository);
    }

    @Test
    public void givenAnExistingSubjectCode_whenGetSubjectHasBeenInvoked_thenRetrieveAndReturnSubjectFromRepository() {
        String code = "MM101";
        Subject stubbedSubject = new Subject("MM101", "Mathematics Level 1");
        when(subjectRepository.existsById(anyString())).thenReturn(true);
        when(subjectRepository.getOne(anyString())).thenReturn(stubbedSubject);

        Subject expectedSubject = stubbedSubject;
        Subject actualSubject = subjectService.getSubject(code);

        assertEquals(expectedSubject, actualSubject);
        verify(subjectRepository).existsById("MM101");
        verify(subjectRepository).getOne("MM101");
    }

    @Test
    public void givenAnNonExistingSubjectCode_whenGetSubjectHasBeenInvoked_thenDoNotRetrieveFromRepositoryAndReturnNull() {
        String code = "MM101";
        when(subjectRepository.existsById(anyString())).thenReturn(false);

        Subject actualSubject = subjectService.getSubject(code);

        assertNull(actualSubject);
        verify(subjectRepository).existsById("MM101");
        verifyNoMoreInteractions(subjectRepository);
    }

    @Test
    public void givenAllSubjectAreToBeRetrieved_whenGetAllSubjectHasBeenInvoked_thenRetrieveAllSubjectAsAListAndReturnTheList() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("MM101", "Mathematics Level 1"));
        subjects.add(new Subject("MM201", "Mathematics Level 2"));
        subjects.add(new Subject("MM301", "Mathematics Level 3"));
        when(subjectRepository.findAll()).thenReturn(subjects);

        int expectedNumberOfSubject = 3;
        int actualNumberOfSubject = subjectService.getAllSubject().size();

        assertEquals(expectedNumberOfSubject, actualNumberOfSubject);
        verify(subjectRepository).findAll();
    }

    @Test
    public void givenASubjectThatHasBeenEdited_whenEditSubjectHasBeenInvoked_thenSaveSubjectIntoRepositoryAndReturnSubject() {
        Subject editedSubject = new Subject("MM101", "Mathematics Level 1");
        when(subjectRepository.existsById(anyString())).thenReturn(true);
        when(subjectRepository.save(any(Subject.class))).thenReturn(editedSubject);

        Subject expectedSubject = editedSubject;
        Subject actualSubject = subjectService.editSubject(editedSubject);

        assertEquals(expectedSubject, actualSubject);
        verify(subjectRepository).existsById("MM101");
        verify(subjectRepository).save(editedSubject);
    }

    @Test
    public void givenTwoEndUserEditingTheSameSubjectAndOneHasCommittedTheChange_whenEditSubjectHasBeenInvokedByTheOtherEndUser_thenDoNotSaveSubjectInToRepositoryAndReturnNull() {
        Subject editSubject = new Subject("MM101", "Mathematics Level 1");
        when(subjectRepository.existsById(anyString())).thenReturn(false);

        Subject actualSubject = subjectService.editSubject(editSubject);

        assertNull(actualSubject);
        verify(subjectRepository).existsById("MM101");
        verifyNoMoreInteractions(subjectRepository);
    }

    @Test
    public void givenASubjectToBeDeletedByIdCode_whenDeleteSubjectHasBeenInvoked_thenDeleteSubjectFromRepository() {
        String code = "MM101";

        subjectService.deleteSubject(code);

        verify(subjectRepository).deleteById(code);
    }
}
