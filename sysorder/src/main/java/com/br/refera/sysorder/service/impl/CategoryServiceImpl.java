package com.br.refera.sysorder.service.impl;

import com.br.refera.sysorder.entity.Category;
import com.br.refera.sysorder.repository.CategoryRepository;
import com.br.refera.sysorder.service.ApiException;
import com.br.refera.sysorder.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getListCategory() throws ApiException {
        List<Category> categoryList = (List<Category>) categoryRepository.findAll();
        if (categoryList.isEmpty()) {
            throw ApiException.notFound("Register not found with this parameters", "Register not found");
        }
        return categoryList;
    }

    @Override
    public Category getCategoryById(Long id) throws ApiException {
        return categoryRepository.findById(id).orElseThrow(() ->
                ApiException.notFound("Register not found with this parameters", "Register not found"));
    }

    @Override
    public Category createCategory(Category category) throws ApiException {
        if (category.getId() != null && categoryRepository.findById(category.getId()).isPresent()) {
            throw ApiException.badRequest("Category already exist with this id", "Bad parameter");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) throws ApiException {
        if (categoryRepository.findById(category.getId()).isEmpty()) {
            throw ApiException.notFound("Category informed not found", "Category not found");
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Category category) throws ApiException {
        if (categoryRepository.findById(category.getId()).isEmpty()) {
            throw ApiException.notFound("Category informed not found", "Category not found");
        }
        categoryRepository.delete(category);
    }
}
