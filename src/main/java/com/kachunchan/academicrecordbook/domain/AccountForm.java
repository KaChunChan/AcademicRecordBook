package com.kachunchan.academicrecordbook.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AccountForm {

    @NotBlank(message = "Forename cannot be blank.")
    @Size(min = 1, max = 30, message = "Forename must be at least 1 character and up to 30 characters.")
    @Pattern(regexp = "(^[a-zA-Z0-9]$)|(^[a-zA-Z0-9][a-zA-Z0-9]$)|(^[a-zA-Z0-9][a-zA-Z0-9- ]*[a-zA-Z0-9]$)",
            message = "Alphanumerical and hyphen characters only. No spaces or hyphen allowed at the beginning or at the end of the forename.")
    private String forename;

    @NotBlank(message = "Surname cannot be blank.")
    @Size(min = 1, max = 30, message = "Surname must be at least 1 character and up to 30 characters.")
    @Pattern(regexp = "(^[a-zA-Z0-9]$)|(^[a-zA-Z0-9][a-zA-Z0-9]$)|(^[a-zA-Z0-9][a-zA-Z0-9- ]*[a-zA-Z0-9]$)",
            message = "Alphanumerical characters only. No spaces allowed at the beginning or at the end of the surname.")
    private String surname;

    @NotBlank(message = "Username cannot be blank.")
    @Size(min = 3, max = 30, message = "Username must be at least 3 character and up to 30 characters.")
    @Pattern(regexp = "(^[a-zA-Z0-9_-]$)|(^[a-zA-Z0-9_-][a-zA-Z0-9_-]$)|(^[a-zA-Z0-9_-][a-zA-Z0-9._-]*[a-zA-Z0-9_-]$)",
            message = "Alphanumerical, periods, hyphen, and underscore characters only. No spaces allowed. No periods allowed at the beginning or at the end of the username.")
    private String username;

    @NotBlank(message = "E-mail cannot be blank.")
    @Size(min = 3, max = 60, message = "E-mail must be at least 3 character and up to 60 characters.")
    @Pattern(regexp = "(^[a-zA-Z0-9]$)|(^[a-zA-Z0-9].[a-zA-Z0-9-_.]*.@.[a-zA-Z0-9-_.]*[a-zA-Z0-9]$)",
            message = "No spaces allowed at the beginning or at the end of the username. Must be valid e-mail address.")
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 5, max = 30, message ="Password must be at least 5 character and up to 30 characters.")
    private String password;

    @NotNull(message = "Role must be selected.")
    private Role role;

}
