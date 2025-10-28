package com.lisandro.gestorfinanzas.service.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lisandro.gestorfinanzas.dto.CategoryDTO.CategoryDTO;
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
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        // 1. Buscar la categoría existente
        Category category = categoryRespository.findById(categoryDTO.id())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + categoryDTO.id()));

        // 2. Actualizar los campos con los datos del DTO
        category.setName(categoryDTO.name());
        category.setDescription(categoryDTO.description());
        category.setEmoji(categoryDTO.emoji());

        // 3. Guardar la categoría actualizada
        Category updatedCategory = categoryRespository.save(category);

        // 4. Convertir la entidad guardada a DTO y retornar
        return new CategoryDTO(
                updatedCategory.getId(),
                updatedCategory.getName(),
                updatedCategory.getDescription(),
                updatedCategory.getEmoji());
    }

}
