package com.kachunchan.academicrecordbook.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SUBJECTS")
public class Subject {
    @Id
    private String code;
    @Column
    private String title;

    public Subject(String code, String title) {
        this.code = code;
        this.title = title;
    }
}

