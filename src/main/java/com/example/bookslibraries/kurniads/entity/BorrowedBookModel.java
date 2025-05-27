package com.example.bookslibraries.kurniads.entity;

import com.example.bookslibraries.kurniads.util.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="borrowed_book")
@Data
@NoArgsConstructor
public class BorrowedBookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_book", updatable = false, nullable = false)
    private BookModel book;

    @ManyToOne
    @JoinColumn(name = "id_member", updatable = false, nullable = false)
    private MemberModel member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    @Column(name = "id_admin")
    private String idAdmin;

    @Column(columnDefinition = "TEXT")
    private String notes;

}
