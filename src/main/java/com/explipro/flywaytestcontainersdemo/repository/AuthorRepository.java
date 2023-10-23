package com.explipro.flywaytestcontainersdemo.repository;

import com.explipro.flywaytestcontainersdemo.model.Author;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {}
