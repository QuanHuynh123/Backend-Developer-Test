package com.example.bookstore.controller;

import com.example.bookstore.entity.Book;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class BookApiController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(
            @RequestParam Optional<Long> authorId,
            @RequestParam Optional<LocalDate> publishedAfter,
            @RequestParam Optional<String> title,
            @RequestParam Optional<Double> minPrice,
            @RequestParam Optional<Double> maxPrice,
            @RequestParam(defaultValue = "false") boolean includeAuthorInfo) {

        List<Book> books = bookService.getAllBooks(authorId, publishedAfter, title, minPrice, maxPrice, includeAuthorInfo);
        return ResponseEntity.ok(books);
    }
}
