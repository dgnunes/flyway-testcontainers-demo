package com.explipro.flywaytestcontainersdemo.repository;


import com.explipro.flywaytestcontainersdemo.FlywayTestcontainersDemoApplication;
import com.explipro.flywaytestcontainersdemo.model.Author;
import java.util.List;
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
}
