package com.example.library_system.repository;

import com.example.library_system.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

public interface BorrowRecordRepository extends PagingAndSortingRepository<BorrowRecord, String>, JpaRepository<BorrowRecord, String>, QueryByExampleExecutor<BorrowRecord> {
    @Query("SELECT br FROM BorrowRecord br WHERE br.book.id = :bookId AND br.user.email = :email")
    BorrowRecord findByBookIdAndUserEmail(String bookId, String email);
}
