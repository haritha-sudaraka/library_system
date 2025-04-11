package com.example.library_system.repository;

import com.example.library_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserRepository extends PagingAndSortingRepository<User, String>, JpaRepository<User, String>, QueryByExampleExecutor<User> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User getByEmail(String email);
}
