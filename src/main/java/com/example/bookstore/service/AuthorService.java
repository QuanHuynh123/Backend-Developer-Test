package com.example.bookstore.service;

import com.example.bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public void deleteAuthor(int authorId){
        authorRepository.deleteById(authorId);
    }
}
