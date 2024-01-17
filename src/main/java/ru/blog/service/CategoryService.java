package ru.blog.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.blog.entity.Category;
import ru.blog.repository.CategoryIdAndName;
import ru.blog.repository.CategoryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryIdAndName> getActivatedCategories() {
        return categoryRepository.getCategoriesByActive(true);
    }

}
