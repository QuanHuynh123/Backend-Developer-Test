package com.example.bookstore.mapper;

import com.example.bookstore.dto.AuthorDTO;
import com.example.bookstore.entity.Author;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO toDTO(Author author);
    Author toEntity(AuthorDTO authorDTO);
}
