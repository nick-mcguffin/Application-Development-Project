package com.wilma.service.forum;

import com.wilma.entity.Category;
import com.wilma.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends CrudOpsImpl<Category, Integer, CategoryRepository> {

}
