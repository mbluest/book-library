package com.example.bookslibraries.kurniads.repository;

import com.example.bookslibraries.kurniads.entity.BorrowedBookModel;
import com.example.bookslibraries.kurniads.util.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBookModel, Long> {

    List<BorrowedBookModel> findByMember_AccountId(String accountId);

    List<BorrowedBookModel> findByMember_AccountIdAndStatus(String accountId, TransactionStatus status);

    List<BorrowedBookModel> findByStatus(TransactionStatus status);

    BorrowedBookModel findById(int id);

    @Query("SELECT b FROM BorrowedBookModel b " +
            "WHERE (:title IS NULL OR LOWER(b.book.title) " +
            "LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:memberName IS NULL OR LOWER(b.member.name) LIKE LOWER(CONCAT('%', :memberName, '%'))) " +
            "AND (:startDate IS NULL OR b.createdDate >= :startDate) AND (:endDate IS NULL OR b.createdDate <= :endDate) " +
            "ORDER BY b.createdDate DESC")
    List<BorrowedBookModel> searchBorrowedBooks(
            @Param("title") String title,
            @Param("memberName") String memberName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    BorrowedBookModel findTopByMember_AccountIdAndBook_IdOrderByCreatedDateDesc(String accountId, int id);

}
