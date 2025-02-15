package com.example.bookstore.service;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.exception.DuplicateISBNException;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    AuthorRepository authorRepository;

    public BookDTO createBook(BookDTO bookDTO) {
        try {
            Author author = authorRepository.findById(bookDTO.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not exist"));


            Book book = bookMapper.toEntity(bookDTO);
            book.setAuthor(author);

            book = bookRepository.save(book);
            return bookMapper.toDTO(book);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateISBNException("ISBN " + bookDTO.getIsbn() + " is exist.");
        }
    }


    public BookDTO getBook(int bookId, boolean includeAuthor) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Can't found book"));

        return includeAuthor ? bookMapper.toDTOWithAuthor(book) : bookMapper.toDTO(book);
    }



    public List<BookDTO> getAllBooks(Long authorId,
                                     Date publishedAfter,
                                     String title,
                                     Double minPrice,
                                     Double maxPrice,
                                     boolean includeAuthorInfo) {
        Specification<Book> spec = Specification.where(null);

        if (authorId != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("author").get("id"), authorId));
        }
        if (publishedAfter != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThan(root.get("published_date"), publishedAfter));
        }
        if (title != null && !title.trim().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("title"), "%" + title + "%"));
        }
        if (minPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        List<Book> books;

        // If no filter condition, get all books
        if (spec == null) {
            books = bookRepository.findAll();
        } else {
            books = bookRepository.findAll(spec);
        }

        if (!includeAuthorInfo) {
            books.forEach(book -> book.setAuthor(null));
        }

        return bookMapper.toDTOList(books);
    }

    @Transactional
    public BookDTO updateBook(BookDTO bookDTO) {
        Book book = bookRepository.findById(bookDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookDTO.getId()));

        // Check ISBN
        if (!book.getIsbn().equals(bookDTO.getIsbn())) {
            try {
                book.setIsbn(bookDTO.getIsbn());
                bookRepository.save(book); // Kiểm tra ngay ở đây
            } catch (DataIntegrityViolationException ex) {
                throw new DuplicateISBNException("ISBN " + bookDTO.getIsbn() + " already exists.");
            }
        }

        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setPrice(bookDTO.getPrice());
        book.setPublished_date(bookDTO.getPublishedDate());

        // If bookDTO have authorId, update id author
        if (bookDTO.getAuthorId() > 0 ) {
            Author author = authorRepository.findById(bookDTO.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + bookDTO.getAuthorId()));
            book.setAuthor(author);
        }

        return bookMapper.toDTO(book);
    }

    public void deleteBook(int bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        bookRepository.delete(book);
    }
}
