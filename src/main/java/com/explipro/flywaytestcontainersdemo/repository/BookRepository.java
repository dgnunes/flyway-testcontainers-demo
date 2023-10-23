package com.explipro.flywaytestcontainersdemo.repository;

import com.explipro.flywaytestcontainersdemo.model.Book;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository  extends JpaRepository<Book, UUID> {}
