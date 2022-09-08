package com.wilma.service.forum;

import com.wilma.entity.Category;
import com.wilma.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends CrudOpsImpl<Category, Integer, CategoryRepository> {

    @Autowired
    CategoryRepository categoryRepository;

    public Category findByName(String categoryName) {
        return categoryRepository.findByNameIgnoreCase(categoryName);
    }
}
