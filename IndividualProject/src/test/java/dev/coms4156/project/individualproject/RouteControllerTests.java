package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.coms4156.project.individualproject.controller.RouteController;
import dev.coms4156.project.individualproject.model.Book;
import dev.coms4156.project.individualproject.service.MockApiService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
* This class contains the unit tests for the RouteController class.
*/
@SpringBootTest
public class RouteControllerTests {

  public static RouteController controller;
  public static MockApiService mockApiService;
  public static List<Book> books;

  /**
   * Setup RouteController for testing.
   */
  @BeforeAll
  public static void setUpRouteControllerForTesting() {
    mockApiService = new MockApiService();
    books = mockApiService.getBooks();
    assertFalse(books.isEmpty());
    controller = new RouteController(mockApiService);
  }

  @Test
  public void getBookTestFound() {

    Book test = books.get(0);

    ResponseEntity<?> res = controller.getBook(test.getId());

    assertEquals(HttpStatus.OK, res.getStatusCode());
    assertEquals(test, res.getBody());

  }

  @Test
  public void getBookTestNotFound() {

    ResponseEntity<?> res = controller.getBook(-1);
    assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    assertEquals("Book not found.",  res.getBody());

  }

  @Test
  public void addCopyTestSuccess() {

    books = mockApiService.getBooks();
    Book book = books.get(0);


    int totalCopies = book.getTotalCopies();
    int availableCopies = book.getCopiesAvailable();

    ResponseEntity<?> res = controller.addCopy(book.getId());
    assertEquals(HttpStatus.OK, res.getStatusCode());

    assertEquals(availableCopies + 1, book.getCopiesAvailable());
    assertEquals(totalCopies + 1, book.getTotalCopies());

  }

  @Test
  public void addCopyTestNotFound() {

    ResponseEntity<?> res = controller.checkoutBook(-1);
    assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    assertEquals("Book not found.",  res.getBody());

  }


  @Test
  public void getRecommendedTestSuccess(){
    ResponseEntity<?> res = controller.getRecommendedBooks();
    assertEquals(HttpStatus.OK, res.getStatusCode());

    List<Book> rec_books = (ArrayList<Book>) res.getBody();
    assertNotNull(rec_books);
    assertEquals(rec_books.size(), new HashSet<>(rec_books).size());

    List<Book> all_books = mockApiService.getBooks();

    all_books.sort((a, b)->{
      return b.getAmountOfTimesCheckedOut() - a.getAmountOfTimesCheckedOut();
    });

    for(int i =0; i<5; i++) {
      assertTrue(rec_books.contains(all_books.get(i)));
    }
  }
  
  @Test
  public void checkoutBookTestSuccess(){

    assertFalse(books.isEmpty());
    Book book = books.get(0);
    book.addCopy();
    mockApiService.updateBook(book);

    int checkedOutCopies = book.getAmountOfTimesCheckedOut();
    int availableCopies = book.getCopiesAvailable();

    ResponseEntity<?> res = controller.checkoutBook(book.getId());

    assertEquals(HttpStatus.OK, res.getStatusCode());
    book = (Book) res.getBody();

    assertNotNull(book);

    assertEquals(availableCopies - 1, book.getCopiesAvailable());
    assertEquals(checkedOutCopies + 1, book.getAmountOfTimesCheckedOut());

    
  }

  @Test
  public void checkoutBookTestConflict(){

    assertFalse(books.isEmpty());

    Book empty = new Book("A", 1);
    empty.checkoutCopy();
    mockApiService.updateBook(empty);

    ResponseEntity<?> res = controller.checkoutBook(empty.getId());
    assertEquals(HttpStatus.CONFLICT, res.getStatusCode());

  }

  @Test
  public void checkoutBookTestNotFound(){

    assertFalse(books.isEmpty());

    ResponseEntity<?> res = controller.checkoutBook(-1);
    assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    assertEquals("Book not found.",  res.getBody());

  }

  @Test
  public void addCopyTest() {

  //WIP

  }

}
