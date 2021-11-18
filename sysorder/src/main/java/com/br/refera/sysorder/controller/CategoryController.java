package com.br.refera.sysorder.controller;

import com.br.refera.sysorder.entity.Category;
import com.br.refera.sysorder.service.ApiException;
import com.br.refera.sysorder.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> findCategories() throws ApiException {
        return ResponseEntity.ok(categoryService.getListCategory());
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<?> findCategoryById(@PathVariable Long categoryId) throws ApiException {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@RequestBody Category category) throws ApiException {
        return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.CREATED);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) throws ApiException {
        category.setId(id);
        return new ResponseEntity<>(categoryService.updateCategory(category), HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) throws ApiException {
        categoryService.deleteCategory(Category.builder().id(id).build());
        return ResponseEntity.noContent().build();
    }

}
