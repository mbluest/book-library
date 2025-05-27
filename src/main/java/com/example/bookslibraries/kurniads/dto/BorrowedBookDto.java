package com.example.bookslibraries.kurniads.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties
@NoArgsConstructor
public class BorrowedBookDto {

    private int id;

    private BookDto book;

    private MemberDto member;

    private String type;

    private String status;

    private String createdDate;

    private String approvedDate;

    private MemberDto admin;

    private String notes;

}
