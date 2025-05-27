package com.example.bookslibraries.kurniads.entity;

import com.example.bookslibraries.kurniads.dto.TimeStampBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="category")
@Data
@NoArgsConstructor
public class CategoryModel extends TimeStampBase{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String category;

    @Column(nullable = false)
    private String description;

}
