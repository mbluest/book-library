package com.example.bookslibraries.kurniads.entity;

import com.example.bookslibraries.kurniads.dto.TimeStampBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="author")
@Data
@NoArgsConstructor
public class AuthorModel extends TimeStampBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String bio;


}
