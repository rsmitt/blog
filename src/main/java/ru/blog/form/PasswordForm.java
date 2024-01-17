package ru.blog.form;


import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PasswordForm {

    @NotEmpty(message = "Please fill out this field")
    @Column(length = 25)
    private String name;
    @NotEmpty(message = "Please fill out this field")
    @Size(min = 6)
    private String password;
}
