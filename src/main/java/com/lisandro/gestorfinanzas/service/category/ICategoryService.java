package com.lisandro.gestorfinanzas.service.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lisandro.gestorfinanzas.dto.CategoryDTO.CategoryDTO;
import com.lisandro.gestorfinanzas.model.Category;

@Service
public interface ICategoryService {

    Category save(CategoryDTO dto, String username);

    void deleteById(Long id);

    List<Category> getAllCategoryUser(String username);

    Category getCategoryById(Long id);

   // CategoryDTO updateCategory(CategoryDTO categoryDTO);

}
