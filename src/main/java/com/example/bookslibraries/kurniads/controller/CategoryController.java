package com.example.bookslibraries.kurniads.controller;

import com.example.bookslibraries.kurniads.dto.CategoryDto;
import com.example.bookslibraries.kurniads.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    @CrossOrigin("*")
    public ResponseEntity create(@RequestHeader("Authorization") String auth,
                                 @RequestBody(required = false) CategoryDto categoryDto){
        return new ResponseEntity(categoryService.createCategory(auth, categoryDto), HttpStatus.OK);
    }

    @PutMapping("/update")
    @CrossOrigin("*")
    public ResponseEntity update(@RequestHeader("Authorization") String auth,
                                 @RequestBody(required = false) CategoryDto categoryDto){
        return new ResponseEntity(categoryService.updateCategory(auth, categoryDto), HttpStatus.OK);
    }

    @GetMapping("/read")
    @CrossOrigin("*")
    public ResponseEntity get(@RequestHeader("Authorization") String auth){
        return new ResponseEntity(categoryService.readCategory(auth), HttpStatus.OK);
    }

    @GetMapping("/read-detail")
    @CrossOrigin("*")
    public ResponseEntity getDetail(@RequestHeader("Authorization") String auth,
                                    @RequestParam int id){
        return new ResponseEntity(categoryService.readDetailCategory(auth, id), HttpStatus.OK);
    }
}
