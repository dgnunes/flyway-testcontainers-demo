package com.explipro.flywaytestcontainersdemo.repository;


import com.explipro.flywaytestcontainersdemo.FlywayTestcontainersDemoApplication;
import com.explipro.flywaytestcontainersdemo.model.Author;
import java.util.List;
import java.util.Optional;
import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = FlywayTestcontainersDemoApplication.class)
@Testcontainers
public class AuthorRepositoryTest {

  @Autowired
  AuthorRepository authorRepository;

  @Autowired
  Flyway flyway;
  @After
  //Destroy and Recreate the DB after every test
  public void cleanup() {
    flyway.clean();
    flyway.migrate();
  }

  @Test
  public void createTest(){
    Author author = new Author();
    author.setName("Stephen King");

    Author created = authorRepository.save(author);
    assertNotNull(created.getId());
    assertEquals(author.getName(), created.getName());
  }

  @Test
  public void findAllTest(){
    Author author1 = new Author();
    author1.setName("Stephen King");
    authorRepository.save(author1);

    Author author2 = new Author();
    author2.setName("Tom Clancy");
    authorRepository.save(author2);

    List<Author> all = authorRepository.findAll();
    assertEquals(2, all.size());
  }

  @Test
  public void findByIdTest(){
    Author author = new Author();
    author.setName("Stephen King");
    Author created = authorRepository.save(author);

    Optional<Author> found = authorRepository.findById(created.getId());
    assertEquals(created.getId(), found.get().getId());
  }

  @Test
  public void updateTest(){
    Author author = new Author();
    author.setName("Stephen King");
    Author created = authorRepository.save(author);

    created.setName("Tom Clancy");
    Author updated = authorRepository.save(created);

    assertEquals(created.getId(), updated.getId());
    assertEquals("Tom Clancy", updated.getName());
  }

  @Test
  public void deleteTest(){
    Author author = new Author();
    author.setName("Stephen King");
    Author created = authorRepository.save(author);
    assertEquals(1, authorRepository.findAll().size());

    authorRepository.delete(created);
    assertEquals(0, authorRepository.findAll().size());
  }
}
