package com.example.bookstore.controller;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.entity.Book;
import com.example.bookstore.exception.DuplicateISBNException;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookApiController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @RequestMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody BookDTO book) {
        try {
            BookDTO savedBook = bookService.createBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
        } catch (DuplicateISBNException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
        }
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBook(
            @PathVariable int bookId,
            @RequestParam(defaultValue = "false") boolean includeAuthor) {

        BookDTO book = bookService.getBook(bookId, includeAuthor);
        return ResponseEntity.ok(book);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks(
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) String publishedAfter,  // Đổi Date -> String
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "false") boolean includeAuthorInfo) {

        Date publishedDate = null;
        if (publishedAfter != null && !publishedAfter.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = sdf.parse(publishedAfter);
                publishedDate = new Date(utilDate.getTime());  // Chuyển thành java.sql.Date
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd.");
            }
        }

        List<BookDTO> books = bookService.getAllBooks(authorId, publishedDate, title, minPrice, maxPrice, includeAuthorInfo);
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO) {
        bookDTO.setId(id); // Make sure the id from path variable and body match
        BookDTO updatedBook = bookService.updateBook(bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
