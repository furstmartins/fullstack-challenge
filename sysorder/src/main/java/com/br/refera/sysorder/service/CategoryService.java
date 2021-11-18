package com.br.refera.sysorder.service;

import com.br.refera.sysorder.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getListCategory() throws ApiException;
    Category getCategoryById(Long id) throws ApiException;
    Category createCategory(Category category) throws ApiException;
    Category updateCategory(Category category) throws ApiException;
    void deleteCategory(Category category) throws ApiException;
}
