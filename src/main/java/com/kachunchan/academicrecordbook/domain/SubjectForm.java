package com.kachunchan.academicrecordbook.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class SubjectForm {

    @NotBlank(message = "Subject ID Code cannot be blank.")
    @Size(min = 5, max = 6, message = "Subject ID Code must contain 5 or 6 alphanumerical characters.")
    @Pattern(regexp = "([a-zA-Z0-9]{5})|([a-zA-Z0-9]{6})",
            message = "Subject ID Code may only have Alphanumerical characters only.")
    private String code;

    @NotBlank(message = "Subject title cannot be blank.")
    @Size(min = 1, max = 64, message = "Subject title must be at least one character and no more than 64 characters long.")
    @Pattern(regexp = "[a-zA-Z0-9 -]+",
            message = "Subject Title may only have Alphanumerical, space, and hyphen characters only.")
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubjectForm that = (SubjectForm) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        return title != null ? title.equals(that.title) : that.title == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
