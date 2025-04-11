package com.example.library_system.controller;

import com.example.library_system.dto.BookDto;
import com.example.library_system.dto.BookTransactionDto;
import com.example.library_system.dto.ResponseDto;
import com.example.library_system.service.BookService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/explore")
    public List<BookDto> getAllBooksFiltered(@RequestParam(value = "title", required = false) String title,
                                             @RequestParam(value = "author", required = false) String author,
                                             @RequestParam(value = "year", required = false) @Pattern(regexp = "\\d{4}") String year) {
        return bookService.getAllBooksFiltered(title, author, year);
    }

    @PostMapping("/borrow")
    public ResponseDto borrowBook(@RequestBody BookTransactionDto bookTransactionDto) {
        return bookService.borrowBook(bookTransactionDto);
    }

    @PutMapping("/return")
    public ResponseDto returnBook(@RequestBody BookTransactionDto bookTransactionDto) {
        return bookService.returnBook(bookTransactionDto);
    }

    @GetMapping("/burrow-history")
    public List<BookDto> getBorrowHistory(@RequestParam(value = "email") String email) {
        return bookService.getBorrowHistory(email);
    }
}
