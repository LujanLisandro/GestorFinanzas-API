package com.lisandro.gestorfinanzas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lisandro.gestorfinanzas.dto.CategoryDTO.CategoryDTO;
import com.lisandro.gestorfinanzas.model.Category;
import com.lisandro.gestorfinanzas.service.category.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categoryList = categoryService.getAllCategory();
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {

        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(Category category) {
        return ResponseEntity.ok(categoryService.save(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        // El service lanza excepción si no existe
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 solo si se eliminó
    }

    @PutMapping
    public ResponseEntity<CategoryDTO> editCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryDTO);
    }
}
