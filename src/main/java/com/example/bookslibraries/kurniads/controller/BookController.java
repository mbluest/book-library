package com.example.bookslibraries.kurniads.controller;

import com.example.bookslibraries.kurniads.dto.BookDto;
import com.example.bookslibraries.kurniads.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/create")
    @CrossOrigin("*")
    public ResponseEntity create(@RequestHeader("Authorization") String auth,
                                 @RequestBody(required = false) BookDto bookDto){
        return new ResponseEntity(bookService.addBook(auth, bookDto), HttpStatus.OK);
    }

    @PutMapping("/update")
    @CrossOrigin("*")
    public ResponseEntity update(@RequestHeader("Authorization") String auth,
                                 @RequestBody(required = false) BookDto bookDto){
        return new ResponseEntity(bookService.updateBook(auth, bookDto), HttpStatus.OK);
    }

    @GetMapping("/read")
    @CrossOrigin("*")
    public ResponseEntity get(@RequestHeader("Authorization") String auth){
        return new ResponseEntity(bookService.readBook(auth), HttpStatus.OK);
    }

    @GetMapping("/read-detail")
    @CrossOrigin("*")
    public ResponseEntity getDetail(@RequestHeader("Authorization") String auth,
                                    @RequestParam int id){
        return new ResponseEntity(bookService.readDetailBook(auth, id), HttpStatus.OK);
    }

}
