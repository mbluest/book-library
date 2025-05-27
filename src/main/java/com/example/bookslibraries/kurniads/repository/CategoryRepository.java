package com.example.bookslibraries.kurniads.repository;

import com.example.bookslibraries.kurniads.entity.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    public Optional<CategoryModel> findById(int id);
}
