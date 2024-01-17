package ru.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.blog.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
