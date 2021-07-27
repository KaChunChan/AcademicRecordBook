package com.kachunchan.academicrecordbook.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
public class ClassForm {

    @NotBlank(message = "Class ID Code cannot be blank.")
    @Size(min = 12, max = 12, message = "Class ID Code must contain 12 alphanumerical characters.")
    @Pattern(regexp = "([a-zA-Z0-9]{12})",
            message = "Class ID Code may only have Alphanumerical characters only.")
    private String code;

    @NotNull(message = "Subject must be selected.")
    private Subject subject;

    private Instructor instructor;

    private Set<Student> students;
}
