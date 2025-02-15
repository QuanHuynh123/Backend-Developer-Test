package com.example.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Không hiện null
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDTO {
    int id;
    String title;
    Date publishedDate;
    String isbn;
    BigDecimal price;
    int authorId;
    AuthorDTO author;
}
