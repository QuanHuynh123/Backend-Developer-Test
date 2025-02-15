package com.example.bookstore.mapper;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface BookMapper {

    @Mapping(source = "published_date", target = "publishedDate")
    @Mapping(source = "author.id", target = "authorId") // only get author id
    @Mapping(target = "author", ignore = true) // defauld dont get infor author
    BookDTO toDTO(Book book);

    // If includeAuthor = true, get full authorDTO
    @Mapping(source = "published_date", target = "publishedDate")
    @Mapping(source = "author", target = "author")
    BookDTO toDTOWithAuthor(Book book);

    @Mapping(source = "publishedDate", target = "published_date")
    @Mapping(source = "authorId", target = "author.id")
    Book toEntity(BookDTO bookDTO);

    default List<BookDTO> toDTOList(List<Book> books) {
        return books.stream().map(this::toDTO).toList();
    }


}
