package com.kachunchan.academicrecordbook.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CLASSES")
public class Class {
    @Id
    private String code;
    @OneToOne
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;
    @OneToOne
    @JoinColumn(name = "INSTRUCTOR_ID")
    private Instructor instructor;
    @ElementCollection
    private Set<Student> students;

    public Class(String code, Subject subject) {
        this.code = code;
        this.subject = subject;
        this.instructor = null;
        this.students = new HashSet<>();
    }
}
