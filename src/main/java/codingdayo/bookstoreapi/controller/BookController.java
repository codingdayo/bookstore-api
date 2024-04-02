package codingdayo.bookstoreapi.controller;

import codingdayo.bookstoreapi.entity.Book;
import codingdayo.bookstoreapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("{id}")
    public ResponseEntity<Book> findById(@PathVariable ("id") Long theId){
        Book theBook = bookService.findById(theId);

        return new ResponseEntity<>(theBook, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks(){
        List<Book> allBooks = bookService.findAll();

        return new ResponseEntity<>(allBooks, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book){



        Book savedBook = bookService.createBook(book);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);

    }

    @PutMapping("{id}")
    public ResponseEntity<Book> editBook(@PathVariable ("id") Long theId, @RequestBody Book book){

     book.setId(theId);

        Book updatedBook = bookService.editBook(book);

        return new ResponseEntity<>(updatedBook, HttpStatus.OK );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBook(@PathVariable ("id") Long theId){

        bookService.delete(theId);

        return new ResponseEntity<>("Book was successfully Deleted", HttpStatus.OK);
    }


}
