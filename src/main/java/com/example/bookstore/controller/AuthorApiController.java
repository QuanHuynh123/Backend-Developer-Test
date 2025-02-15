package com.example.bookstore.controller;

import com.example.bookstore.dto.AuthorDTO;
import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.exception.DuplicateISBNException;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/authors")
public class AuthorApiController {

    @Autowired
    AuthorService authorService;
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable int id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.ok(Map.of("message", "Author deleted successfully"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    @RequestMapping("/add")
    public ResponseEntity<?> addAuthor(@RequestBody AuthorDTO author) {
        AuthorDTO savedAuthor = authorService.createAuthor(author);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
    }
}
