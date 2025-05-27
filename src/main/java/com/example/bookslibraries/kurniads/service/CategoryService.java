package com.example.bookslibraries.kurniads.service;

import com.example.bookslibraries.kurniads.dto.CategoryDto;

import java.util.Map;

public interface CategoryService {

    public Map<String, Object> createCategory(String auth, CategoryDto categoryDto);

    public Map<String, Object> updateCategory(String auth, CategoryDto categoryDto);

    public Map<String, Object> readCategory(String auth);

    public Map<String, Object> readDetailCategory(String auth, int id);
}
