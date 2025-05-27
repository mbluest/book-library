package com.example.bookslibraries.kurniads.controller;

import com.example.bookslibraries.kurniads.dto.AuthorDto;
import com.example.bookslibraries.kurniads.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/create")
    @CrossOrigin("*")
    public ResponseEntity create(@RequestHeader("Authorization") String auth,
                                 @RequestBody(required = false) AuthorDto authorDto){
        return new ResponseEntity(authorService.createAuthor(auth, authorDto), HttpStatus.OK);
    }

    @PutMapping("/update")
    @CrossOrigin("*")
    public ResponseEntity update(@RequestHeader("Authorization") String auth,
                                 @RequestBody(required = false) AuthorDto authorDto){
        return new ResponseEntity(authorService.updateAuthor(auth, authorDto), HttpStatus.OK);
    }

    @GetMapping("/read")
    @CrossOrigin("*")
    public ResponseEntity get(@RequestHeader("Authorization") String auth){
        return new ResponseEntity(authorService.readAuthor(auth), HttpStatus.OK);
    }

    @GetMapping("/read-detail")
    @CrossOrigin("*")
    public ResponseEntity getDetail(@RequestHeader("Authorization") String auth,
                                    @RequestParam int id){
        return new ResponseEntity(authorService.readAuthorById(auth, id), HttpStatus.OK);
    }
}
