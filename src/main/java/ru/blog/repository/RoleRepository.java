package ru.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.blog.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
