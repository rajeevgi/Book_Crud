package com.sprk.book_crud.service;

import java.util.List;

import com.sprk.book_crud.entity.Book;

public interface BookService {

    boolean saveBook(Book book);

    List<Book> getAllBooks();

    Book getBookById(int bookId);

    void deleteBookById(Book book);
}
