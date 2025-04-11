package com.example.library_system.repository;

import com.example.library_system.dto.BookDto;
import com.example.library_system.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book, String>, JpaRepository<Book, String>, QueryByExampleExecutor<Book> {

    @Query("SELECT b FROM Book b WHERE " +
            "((:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) OR " +
            "(:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) OR " +
            "(:publishedYear IS NULL OR b.publishedYear LIKE CONCAT('%', :publishedYear, '%'))) AND " +
            "b.availableCopies > 0")
    List<Book> getAllBooksFiltered(@Param("title") String title,
                           @Param("author") String author,
                           @Param("publishedYear") String publishedYear);
}
