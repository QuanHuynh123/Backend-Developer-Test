package com.example.bookstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "book", uniqueConstraints = @UniqueConstraint(columnNames = "isbn"))
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @NotBlank(message = "Title not null")
    @Column(name = "title", nullable = false)
    String title;

    @NotNull(message = "Published_date not null")
    @Column(name = "published_date", nullable = false)
    Date published_date;

    @NotBlank(message = "ISBN not null")
    @Column(name = "isbn", nullable = false, unique = true)
    String isbn;

    @NotNull(message = "Price not null")
    @Positive(message = "Price > 0 ")
    @Column(name = "price", nullable = false)
    BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Author not null")
    @JoinColumn(name = "author_id", nullable = false)
    Author author;
}
