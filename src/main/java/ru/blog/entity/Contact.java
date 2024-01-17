package ru.blog.entity;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Please fill out this field")
    @Size(min = 1)
    private String name;
    @NotEmpty(message = "Please fill out this field")
    @URL(regexp = "^(http|https).*")
    private String url;
    private boolean active;

}
