package com.example.bookstore.service;

import com.example.bookstore.dto.AuthorDTO;
import com.example.bookstore.entity.Author;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.mapper.AuthorMapper;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorMapper authorMapper;

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        author = authorRepository.save(author);
        return authorMapper.toDTO(author);
    }

    public void deleteAuthor(int authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + authorId));

        // Kiểm tra nếu tác giả còn sách thì không cho xóa
        if (bookRepository.existsByAuthorId(authorId)) {
            throw new IllegalStateException("Cannot delete author with existing books.");
        }

        authorRepository.delete(author);
    }
}
