package com.example.bookslibraries.kurniads.service;

import com.example.bookslibraries.kurniads.dto.BorrowedBookDto;
import com.example.bookslibraries.kurniads.util.enums.TransactionStatus;
import java.time.LocalDateTime;
import java.util.Map;

public interface BorrowedBookService {

    public Map<String, Object> readAll(String auth, TransactionStatus status);

    public Map<String, Object> readByUser(String auth, String accountId, TransactionStatus status);

    public Map<String, Object> readByFilter(String auth, String title, String memberName, LocalDateTime startDate, LocalDateTime endDate);

    public Map<String, Object> doBorrowBook(String auth, int idBook, String idMember);

    public Map<String, Object> doApproveBorrowBook(String auth, BorrowedBookDto borrowedBookDto);

    public Map<String, Object> doRejectBorrowBook(String auth, BorrowedBookDto borrowedBookDto);

    public Map<String, Object> doReturnBook(String auth, BorrowedBookDto borrowedBookDto);

    public Map<String, Object> findBookByUser(String auth, String accountId, int bookId);

}
