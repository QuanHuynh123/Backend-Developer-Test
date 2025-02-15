package com.example.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "author")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @NotBlank(message = "Name author not null")
    @Column(name = "name", nullable = false)
    String name;

    @NotBlank(message = "Nationality not null")
    @Column(name = "nationality", nullable = false)
    String nationality;

    @OneToMany(mappedBy = "author",fetch = FetchType.LAZY)
    private List<Book> books;
}
