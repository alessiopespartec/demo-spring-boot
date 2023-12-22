package com.example.demo.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

//    public Book findTitleByYearAndTitle(LocalDate date, String title);
//
//    @Query(value = "Select Book b where b.title= :titleparam and b.year = :yearparam")
//    public Book getPippo(@Param("titleparam") String bookTitle, @Param("yearparam") LocalDate year);
//
//    @Query(value = "Select * FROM book b where b.title= ?1 and b.year = ?2 ", nativeQuery = true)
//    public Book getPippoNative(String bookTitle, LocalDate year);


}