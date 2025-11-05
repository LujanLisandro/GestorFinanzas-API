package com.lisandro.gestorfinanzas.service.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lisandro.gestorfinanzas.dto.CategoryDTO.CategoryDTO;
import com.lisandro.gestorfinanzas.model.Category;
import com.lisandro.gestorfinanzas.model.UserSec;
import com.lisandro.gestorfinanzas.repository.ICategoryRepository;
import com.lisandro.gestorfinanzas.service.user.IUserService;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IUserService userService;

    @Override
    public Category save(CategoryDTO dto, String username) {
        UserSec user = userService.findByUsername(username);
        Category category = new Category();
        category.setName(dto.name());
        category.setDescription(dto.description());
        category.setEmoji(dto.emoji());
        category.setUser(user);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getAllCategoryUser(String username) {
        UserSec user = userService.findByUsername(username);
        List <Category> categoryList = user.getCategories();
        return categoryList;
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id, String username) {
        // 1. Buscar la categoría existente
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));

        // 2. Actualizar los campos con los datos del DTO
        category.setName(categoryDTO.name());
        category.setDescription(categoryDTO.description());
        category.setEmoji(categoryDTO.emoji());

        // 3. Guardar la categoría actualizada
        Category updatedCategory = categoryRepository.save(category);

        // 4. Convertir la entidad guardada a DTO y retornar
        return new CategoryDTO(
                updatedCategory.getName(),
                updatedCategory.getDescription(),
                updatedCategory.getEmoji());
    }

}
