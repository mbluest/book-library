package com.example.bookslibraries.kurniads.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties
@NoArgsConstructor
public class AuthorDto {

    private Integer id;

    private String name;

    private String bio;

}
