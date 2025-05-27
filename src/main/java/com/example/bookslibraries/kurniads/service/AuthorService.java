package com.example.bookslibraries.kurniads.service;

import com.example.bookslibraries.kurniads.dto.AuthorDto;

import java.util.Map;

public interface AuthorService {

    public Map<String, Object> createAuthor(String auth, AuthorDto authorDto);

    public Map<String, Object> updateAuthor(String auth, AuthorDto authorDto);

    public Map<String, Object> readAuthor(String auth);

    public Map<String, Object> readAuthorById(String auth, int id);

}
