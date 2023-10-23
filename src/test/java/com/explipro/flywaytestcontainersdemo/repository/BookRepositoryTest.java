package com.explipro.flywaytestcontainersdemo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.explipro.flywaytestcontainersdemo.FlywayTestcontainersDemoApplication;
import com.explipro.flywaytestcontainersdemo.model.Author;
import com.explipro.flywaytestcontainersdemo.model.Book;
import java.util.Optional;
import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = FlywayTestcontainersDemoApplication.class)
@Testcontainers
public class BookRepositoryTest {
  @Autowired
  AuthorRepository authorRepository;

  @Autowired
  BookRepository bookRepository;

  @Autowired
  Flyway flyway;
  @After
  //Destroy and Recreate the DB after every test
  public void cleanup() {
    flyway.clean();
    flyway.migrate();
  }

  @Test
  public void createTEst(){
    Author author = new Author();
    author.setName("Stephen King");
    Author createdAuthor = authorRepository.save(author);

    Book book = new Book();
    book.setName("The Shining");
    book.setAuthor(createdAuthor);

    Book createdBook = bookRepository.save(book);
    assertNotNull(createdBook.getId());
    assertEquals(book.getName(), createdBook.getName());
  }

  @Test
  public void findAllTest(){
    Author author = new Author();
    author.setName("Stephen King");
    Author createdAuthor = authorRepository.save(author);

    Book book1 = new Book();
    book1.setName("The Shining");
    book1.setAuthor(createdAuthor);
    bookRepository.save(book1);

    Book book2 = new Book();
    book2.setName("Green Mile");
    book2.setAuthor(createdAuthor);
    bookRepository.save(book2);

    assertEquals(2, bookRepository.findAll().size());
  }

  @Test
  public void findByIdTest(){
    Author author = new Author();
    author.setName("Stephen King");
    Author createdAuthor = authorRepository.save(author);

    Book book = new Book();
    book.setName("The Shining");
    book.setAuthor(createdAuthor);

    Book createdBook = bookRepository.save(book);

    Optional<Book> found = bookRepository.findById(createdBook.getId());
    assertTrue(found.isPresent());
    assertEquals(createdBook.getId(), found.get().getId());
  }

  @Test
  public void updateTest(){
    Author author = new Author();
    author.setName("Stephen King");
    Author createdAuthor = authorRepository.save(author);

    Book book = new Book();
    book.setName("IT");
    book.setAuthor(createdAuthor);

    Book createdBook = bookRepository.save(book);
    createdBook.setName("IT 2");

    Book updatedBook = bookRepository.save(createdBook);
    assertEquals(createdBook.getId(), updatedBook.getId());
    assertEquals("IT 2", updatedBook.getName());
  }

  @Test
  public void deleteTest(){
    Author author = new Author();
    author.setName("Stephen King");
    Author createdAuthor = authorRepository.save(author);

    Book book = new Book();
    book.setName("The Shining");
    book.setAuthor(createdAuthor);
    Book createdBook = bookRepository.save(book);

    assertEquals(1, bookRepository.findAll().size());

    bookRepository.delete(createdBook);
    assertEquals(0, bookRepository.findAll().size());
  }
}
