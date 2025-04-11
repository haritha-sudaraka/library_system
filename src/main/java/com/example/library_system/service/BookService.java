package com.example.library_system.service;

import com.example.library_system.dto.BookDto;
import com.example.library_system.dto.BookTransactionDto;
import com.example.library_system.dto.ResponseDto;
import com.example.library_system.entity.Book;
import com.example.library_system.entity.BorrowRecord;
import com.example.library_system.entity.User;
import com.example.library_system.repository.BookRepository;
import com.example.library_system.repository.BorrowRecordRepository;
import com.example.library_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    public List<BookDto> getAllBooksFiltered(String title, String author, String year) {
        return bookRepository.getAllBooksFiltered(title, author, year).stream()
                .map(book -> BookDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .year(book.getPublishedYear())
                        .availableCopies(book.getAvailableCopies())
                        .build())
                .toList();
    }

    public ResponseDto borrowBook(BookTransactionDto bookTransactionDto) {
        User user = userRepository.getByEmail(bookTransactionDto.getEmail());
        Book book = bookRepository.findById(bookTransactionDto.getBookId()).orElse(null);
        if (book != null && user != null){
            BorrowRecord borrowRecord = BorrowRecord.builder()
                    .user(user)
                    .book(book)
                    .borrowedAt(LocalDateTime.now())
                    .build();

            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);
            borrowRecordRepository.save(borrowRecord);
            return ResponseDto.builder()
                    .message("Book borrowed successfully")
                    .build();
        }

        return ResponseDto.builder()
                .message("Error Occurred")
                .build();
    }

    @Transactional
    public ResponseDto returnBook(BookTransactionDto bookTransactionDto) {
        BorrowRecord borrowRecord = borrowRecordRepository
                .findByBookIdAndUserEmail(bookTransactionDto.getBookId(), bookTransactionDto.getEmail());
        if (borrowRecord != null) {
            Book book = bookRepository.findById(bookTransactionDto.getBookId()).orElse(null);
            if (book != null) {
                book.setAvailableCopies(book.getAvailableCopies() + 1);
                bookRepository.save(book);
                borrowRecord.setReturnedAt(LocalDateTime.now());
                borrowRecordRepository.save(borrowRecord);
                return ResponseDto.builder()
                        .message("Book returned successfully")
                        .build();
            }
        }
        return ResponseDto.builder()
                .message("Error Occurred")
                .build();
    }
}
