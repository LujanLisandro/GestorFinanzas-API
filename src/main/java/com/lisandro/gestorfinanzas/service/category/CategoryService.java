package com.lisandro.gestorfinanzas.service.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lisandro.gestorfinanzas.model.Category;
import com.lisandro.gestorfinanzas.repository.ICategoryRepository;

public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRespository;

    @Override
    public Category createCategory(Category category) {
        return categoryRespository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRespository.deleteById(id);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRespository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRespository.findById(id).orElse(null);
    }

    @Override
    public Category updateCategory(String name, String description, String emoji) {

    }

}
