package com.well.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.well.demo.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
