package com.example.bookslibraries.kurniads.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties
public class CategoryDto {

    private Integer id;

    private String category;

    private String description;
}
