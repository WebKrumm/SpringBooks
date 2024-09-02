package com.example.springbooks.controller;

import com.example.springbooks.controller.exception.CustomException;
import com.example.springbooks.entity.Book;
import com.example.springbooks.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private final BookRepository repository;
    public BookController(BookRepository repository) {
        this.repository = repository;
    }
    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody Book book, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Book savedBook = repository.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return repository.findAll();
    }
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Book not found"));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody Book updatedBook, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        return repository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    Book savedBook = repository.save(book);
                    return new ResponseEntity<>(savedBook, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/author/{author}")
    public List<Book> getBookByAuthor(@PathVariable String author) {
        return repository.findByAuthor(author);
    }

//    @GetMapping
//    public Page<Book> getBooks(
//        @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "10") int size,
//        @RequestParam(defaultValue = "id") String sortBy
//    ){
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
//        return repository.findAll(pageable);
//    }
    @GetMapping("/date")
    public LocalDateTime getFirst(){
        return LocalDateTime.now();
    }
    @GetMapping("/title/{title}")
    public List<Book> getBookByTitle(@PathVariable String title) {
        return repository.findByTitle(title);
    }


}
