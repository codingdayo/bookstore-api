package codingdayo.bookstoreapi.service;

import codingdayo.bookstoreapi.entity.Book;

import java.util.List;

public interface BookService {

    Book createBook(Book book);

    Book findById(Long theId);

    List<Book> findAll();

    Book editBook(Book book);

    void delete(Long theId);

}
