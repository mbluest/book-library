package com.example.bookslibraries.kurniads.repository;

import com.example.bookslibraries.kurniads.entity.AuthorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorModel, Long> {

    public Optional<AuthorModel> findById(int id);

}
