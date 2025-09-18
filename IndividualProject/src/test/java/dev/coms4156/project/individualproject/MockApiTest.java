package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.coms4156.project.individualproject.model.Book;
import dev.coms4156.project.individualproject.service.MockApiService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * This class contains the unit tests for the MockApiService class.
 */
@SpringBootTest
public class MockApiTest {

  public static MockApiService mockApiService;

  @BeforeAll
  public static void setUpMockApiForTesting() {
    mockApiService = new MockApiService();
  }

  @Test
  public void updateBookTestComprehensive() {
    List<Book> original = mockApiService.getBooks();
    assertFalse(original.isEmpty());

    Book test = new Book("UniqueTestTitle", original.get(0).getId());
    Book test2 = new Book("BNF", -1); // assumes -1 is never used as an id
    mockApiService.updateBook(test);
    mockApiService.updateBook(test2);

    List<Book> updated = mockApiService.getBooks();
    assertEquals(original.size(), updated.size());
    assertTrue(updated.contains(test));
    assertFalse(updated.contains(test2));

  }

}
