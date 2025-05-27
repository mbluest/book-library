package com.example.bookslibraries.kurniads.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties
@NoArgsConstructor
public class BookDto {

    private int id;

    private String title;

    private String imageUrl;

    private String category;

    private int publishingYear;

    private String author;

    private int available;

    private int total;

    private String description;

}
