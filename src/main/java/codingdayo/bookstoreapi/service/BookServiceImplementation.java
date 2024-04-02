package codingdayo.bookstoreapi.service;

import codingdayo.bookstoreapi.entity.Book;
import codingdayo.bookstoreapi.repository.BookRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImplementation implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book createBook(Book book) {

        if(bookRepository.existsByTitle(book.getTitle())){
            throw new RuntimeException("Book Already Exists, won't add new book lol");
        }


        Book newBook = Book.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .summary(book.getSummary())
                .price(String.valueOf("$" + book.getPrice()))
                .build();

        return bookRepository.save(newBook);
    }

    @Override
    public Book findById(Long theId) {

        Optional<Book> optionalBook = bookRepository.findById(theId);

        Book theBook = null;

        if (optionalBook.isPresent()) {
            theBook = optionalBook.get();
        }
        else {

            throw new RuntimeException("Did not find Book id - " + theId);
        }

        return theBook;


        //this didn't work because theBook.get() didn't have isPresent().
        //Optional<Book> theBook = bookRepository.findById(theId);
        //
        //
        //
        //return theBook.get();

    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book editBook(Book book) {
        Book existingBook = bookRepository.findById(book.getId()).get();
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setSummary(book.getSummary());
        existingBook.setPrice(String.valueOf("$" + book.getPrice()));

        Book updatedBook = bookRepository.save(existingBook);

        return updatedBook;

    }

    @Override
    public void delete(Long theId) {
        bookRepository.deleteById(theId);
    }
}
