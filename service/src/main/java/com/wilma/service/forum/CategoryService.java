package com.wilma.service.forum;

import com.wilma.entity.forum.ForumCategory;
import com.wilma.repository.CategoryRepository;
import com.wilma.service.CrudOpsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends CrudOpsImpl<ForumCategory, Integer, CategoryRepository> {

    @Autowired
    CategoryRepository categoryRepository;

    public ForumCategory findByName(String categoryName) {
        return categoryRepository.findByNameIgnoreCase(categoryName);
    }
}
