package com.example.bookstore.service;

import com.example.bookstore.entity.Book;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public Book  addBook(Book book){
        return bookRepository.save(book);
    }

    public Book getBook(int bookId){
        return bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Can't found book"));
    }

    public List<Book> getAllBooks(Optional<Long> authorId,
                                  Optional<LocalDate> publishedAfter,
                                  Optional<String> title,
                                  Optional<Double> minPrice,
                                  Optional<Double> maxPrice,
                                  boolean includeAuthorInfo) {
        Specification<Book> spec = Specification.where(null);

        if (authorId.isPresent()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("author").get("id"), authorId.get()));
        }
        if (publishedAfter.isPresent()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThan(root.get("publishedDate"), publishedAfter.get()));
        }
        if (title.isPresent()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("title"), "%" + title.get() + "%"));
        }
        if (minPrice.isPresent()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice.get()));
        }
        if (maxPrice.isPresent()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice.get()));
        }

        return bookRepository.findAll((Sort) spec);
    }

    public Book updateBook(Book book){
        if (bookRepository.existsById(book.getId()))
            return null;
        else return bookRepository.save(book);
    }

    public void deleteBook(int bookId){
        bookRepository.deleteById(bookId);
    }

}
