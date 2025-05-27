package com.example.bookslibraries.kurniads.repository;

import com.example.bookslibraries.kurniads.entity.BookModel;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookModel, Long> {

    Optional<BookModel> findById(int id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM BookModel b WHERE b.id = :id")
    Optional<BookModel> findByIdForUpdate(@Param("id") int id);
}
