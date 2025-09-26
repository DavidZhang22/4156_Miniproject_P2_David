package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.coms4156.project.individualproject.model.Book;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class contains the unit tests for the Book class.
 */
@SpringBootTest
public class BookUnitTests {

  public static Book book;
  public static Book book2;

  @BeforeAll
  public static void setUpBookForTesting() {
    book = new Book("When Breath Becomes Air", 0);
    book2 = new Book("New Book", 1);
  }

  @Test
  public void equalsBothAreTheSameTest() {
    Book cmpBook = book;
    assertEquals(cmpBook, book);
  }

  @Test
  public void equalsBothAreTheSameTest2() {
    assertNotEquals(book, book2);
  }

  @Test
  public void compareTest() {
    Book cmpBook = book;
    assertEquals(0, book.compareTo(cmpBook));
    assertTrue(book.compareTo(book2) < 0);
    assertTrue(book2.compareTo(book) > 0);
  }

  @Test
  public void addCopiesTest() {
    int totalCopies = book.getTotalCopies();
    int availableCopies = book.getCopiesAvailable();

    book.addCopy();

    assertEquals(totalCopies + 1, book.getCopiesAvailable());
    assertEquals(availableCopies + 1, book.getTotalCopies());

  }

  @Test
  public void hasMultipleAuthorsTest() {

    ArrayList<String> newAuthors = new ArrayList<>();
    newAuthors.add("David");
    book.setAuthors(newAuthors);
    assertFalse(book.hasMultipleAuthors());
    newAuthors.add("John");
    book.setAuthors(newAuthors);
    assertTrue(book.hasMultipleAuthors());

  }

  @Test
  public void checkoutTestSuccess() {
    book.addCopy();

    int checkedOutCopies = book.getAmountOfTimesCheckedOut();
    int availableCopies = book.getCopiesAvailable();

    String dueDate = book.checkoutCopy();
    assertEquals(availableCopies - 1, book.getCopiesAvailable());
    assertEquals(checkedOutCopies + 1, book.getAmountOfTimesCheckedOut());

    List<String> dueDates = book.getReturnDates();
    assertTrue(dueDates.contains(dueDate));
  }

  @Test
  public void checkoutTestNoCopies() {
    Book empty = new Book("", 2);
    empty.checkoutCopy();
    assertEquals(0, empty.getCopiesAvailable());
    assertNull(empty.checkoutCopy());
  }

  @Test public void returnCopyTestSuccess() {
    Book test = new Book("", 2);
    String date = test.checkoutCopy();
    assertNotNull(date);
    assertEquals(0, test.getCopiesAvailable());
    assertTrue(test.returnCopy(date));
  }

  @Test
  public void returnCopyTestFail() {
    Book test = new Book("", 2);
    assertFalse(test.returnCopy(null));
    assertNotNull(test.checkoutCopy());
    assertFalse(test.returnCopy(null));
  }

  @Test
  public void deleteCopyTestSuccess() {
    Book empty = new Book("", 2);
    assertTrue(empty.deleteCopy());
    assertEquals(0, empty.getCopiesAvailable());
  }

  @Test
  public void deleteCopyTestFail() {
    Book test = new Book("", 2);
    assertNotNull(test.checkoutCopy());
    assertFalse(test.deleteCopy());
  }

}
