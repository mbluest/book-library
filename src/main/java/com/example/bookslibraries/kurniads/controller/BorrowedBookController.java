package com.example.bookslibraries.kurniads.controller;

import com.example.bookslibraries.kurniads.dto.BorrowedBookDto;
import com.example.bookslibraries.kurniads.service.BorrowedBookService;
import com.example.bookslibraries.kurniads.util.enums.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/booking")
public class BorrowedBookController {

    @Autowired
    BorrowedBookService borrowedBookService;

    @PostMapping("/create")
    @CrossOrigin("*")
    public ResponseEntity create(@RequestHeader("Authorization") String auth,
                                 @RequestParam int idBook,
                                 @RequestParam String accountId){
        return new ResponseEntity(borrowedBookService.doBorrowBook(auth, idBook, accountId), HttpStatus.OK);
    }

    @PutMapping("/approve")
    @CrossOrigin("*")
    public ResponseEntity approve(@RequestHeader("Authorization") String auth,
                                  @RequestBody(required = false) BorrowedBookDto borrowedBookDto){
        return new ResponseEntity(borrowedBookService.doApproveBorrowBook(auth, borrowedBookDto), HttpStatus.OK);
    }

    @PutMapping("/reject")
    @CrossOrigin("*")
    public ResponseEntity reject(@RequestHeader("Authorization") String auth,
                                  @RequestBody(required = false) BorrowedBookDto borrowedBookDto){
        return new ResponseEntity(borrowedBookService.doRejectBorrowBook(auth, borrowedBookDto), HttpStatus.OK);
    }

    @PutMapping("/returning")
    @CrossOrigin("*")
    public ResponseEntity returning(@RequestHeader("Authorization") String auth,
                                 @RequestBody(required = false) BorrowedBookDto borrowedBookDto){
        return new ResponseEntity(borrowedBookService.doReturnBook(auth, borrowedBookDto), HttpStatus.OK);
    }

    @GetMapping("/read")
    @CrossOrigin("*")
    public ResponseEntity read(@RequestHeader("Authorization") String auth,
                               @RequestParam(required = false) String title,
                               @RequestParam(required = false)  String memberName,
                               @RequestParam(required = false)  LocalDateTime startDate,
                               @RequestParam(required = false)  LocalDateTime endDate){
        return new ResponseEntity(borrowedBookService.readByFilter(auth, title, memberName, startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/read-detail-user")
    @CrossOrigin("*")
    public ResponseEntity readDetailuser(@RequestHeader("Authorization") String auth,
                                         @RequestParam String accountId,
                                         @RequestParam(required = false) TransactionStatus status){
        return new ResponseEntity(borrowedBookService.readByUser(auth, accountId, status), HttpStatus.OK);
    }

    @GetMapping("/find-book-user")
    @CrossOrigin("*")
    public ResponseEntity findBookingUser(@RequestHeader("Authorization") String auth,
                                          @RequestParam String accountId,
                                          @RequestParam int idBook){
        return new ResponseEntity(borrowedBookService.findBookByUser(auth, accountId, idBook), HttpStatus.OK);
    }
}
