package com.example.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name= "book")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "title")
    String title;

    @Column(name = "publishedDate" , nullable = false)
    Date published_date ;

    @Column(name = "price", nullable = false)
    BigDecimal price  ;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    Author author;
}
