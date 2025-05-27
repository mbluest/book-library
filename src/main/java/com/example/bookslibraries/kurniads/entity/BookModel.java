package com.example.bookslibraries.kurniads.entity;

import com.example.bookslibraries.kurniads.dto.TimeStampBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="books")
@Data
@NoArgsConstructor
public class BookModel extends TimeStampBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", name="image_url")
    private String imageUrl;

    @Column(nullable = false)
    private String category;

    @Column(name="publishing_year", nullable = false)
    private int publishingYear;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private int available;

    @Column(nullable = false)
    private int total;

    @Column(columnDefinition = "TEXT", name="description")
    private String description;

    @Column(nullable = false)
    private int isActive;

}
