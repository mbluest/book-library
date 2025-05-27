package com.example.bookslibraries.kurniads.service;

import com.example.bookslibraries.kurniads.dto.BookDto;
import com.example.bookslibraries.kurniads.entity.BookModel;

import java.util.Map;

public interface BookService {

    public Map<String, Object> addBook(String auth, BookDto bookDto);

    public Map<String, Object> updateBook(String auth, BookDto bookDto);

    public Map<String, Object> readBook(String auth);

    public Map<String, Object> readDetailBook(String auth, int idBook);

    public BookModel getBookByIdLock(int id);

    public BookModel getDataBookById(int id);

    public void doBorrow(BookModel bookModel);

    public void doReturn(BookModel bookModel);

}
