package ru.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.blog.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c.id as id, c.name as name from Category c WHERE c.active = :active ORDER BY c.name")
    List<CategoryIdAndName> getCategoriesByActive(@Param("active") boolean active);

}
