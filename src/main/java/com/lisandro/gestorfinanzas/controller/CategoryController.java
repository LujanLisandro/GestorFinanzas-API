package com.lisandro.gestorfinanzas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import com.lisandro.gestorfinanzas.service.category.ICategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(Authentication auth) {
        List<Category> categoryList = categoryService.getAllCategoryUser(auth.getName());
        return ResponseEntity.ok(categoryList);
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {

        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }*/

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO, Authentication auth) {
        Category category = categoryService.save(categoryDTO, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        // El service lanza excepción si no existe
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 solo si se eliminó
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> editCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id, Authentication auth) {
        CategoryDTO editCategory = categoryService.updateCategory(categoryDTO, id, auth.getName());
        return ResponseEntity.ok(editCategory);
    }
}
