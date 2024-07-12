package com.sprk.book_crud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @Column(length = 50)
    @NotBlank(message = "Fields can't be empty!")
    private String bookName;

    @Column(length = 50)
    @NotBlank(message = "Fields can't be empty!")
    private String authorName;

    @Column(columnDefinition =" double default 0")
    private double amount;

    @NotBlank(message = "Fields can't be empty!")
    private String publication;

    @NotBlank(message = "Fields can't be empty!")
    private String edition;

    @NotBlank(message = "Fields can't be empty!")
    @Email(message = "Please enter valid email address!")
    private String email;

    public Book(Book book){
        this.bookId = book.bookId;
        this.authorName = book.authorName;
        this.amount = book.amount;
        this.bookName = book.bookName;
        this.edition = book.edition;
        this.email = book.email;
        this.publication = book.publication;
    }
}
