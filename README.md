Copied from 4156-Miniproject-2025-Students template

# Individual Project – Library API

A minimal Spring Boot REST API for managing books. Exposes endpoints to fetch books, list available titles, get recommendations, and manage copies (add and checkout).


## Prerequisites

- Windows 10
- Java JDK `17` installed https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
- Maven 3.9.5 installed https://maven.apache.org/download.cgi
- PMD https://pmd.github.io/ or through maven.
- Git
- Intellij

## Clone

```
git clone https://github.com/DavidZhang22/4156_Miniproject_P2_David.git
cd 4156_Miniproject_P2_David/
```
## Testing

Code must pass all tests listed below without warning or violations

PMD:
```commandline
    pmd.bat check -d ./IndividualProject/src -R rulesets/java/quickstart.xml -f text
```
or through pmd:check in the Maven tool window on Intellij.
PMD must generate an empty report at [IndividualProject/target/site/pmd.html](IndividualProject/target/site/pmd.html
)

Checkstyle:
```commandline
mvn checkstyle:check
mvn checkstyle:checkstyle
```
Check must yield no warnings and violations and checkstyle generate an empty report at [IndividualProject/target/site/checkstyle.html
](./IndividualProject/target/site/checkstyle.html)

The style guide used is the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)

Tests:
```commandline
mvn clean test
mvn jacoco:report
```
Runs tests and generates the report at [IndividualProject/target/site/jacoco/index.html](IndividualProject/target/site/jacoco/index.html)
showing branch coverage of the tests run.

## Continuous Integration
My project runs CI through Github Actions, using this [workflow](.github/workflows/ci.yml). It runs mvn verify for all pushes/pulls from main.

## Running a Local Instance

```commandline
mvn compile
mvn spring-boot:run
```
Running this command for the first time should install the necessary dependencies. The
default hostname is `http://localhost:8080`

## API Endpoints

Base URL: `http://localhost:8080`

---

### Root / Index
**GET** `/`  
**GET** `/index`  
Index page. Useful for checking that the API is running.

---

### Get Book by ID
**GET** `/book/{id}`

- **Path Parameter**:
    - `id` (integer): unique identifier of the book
- **Responses**:
    - `200 OK` → returns a `Book` object
    - `404 Not Found` → `"Book not found."`

---

### List Available Books
**GET** `/books/available`

- **Description**: Returns all books that currently have available copies.
- **Responses**:
    - `200 OK` → returns a list of `Book` objects
    - `500 Internal Server Error` → `"Error occurred when getting all available books"`

---

### Recommended Books
**GET** `/books/recommendation`

- **Description**: Returns a list of up to 10 unique recommended books.
    - If there are fewer than 10 books in the database, returns all of them.
    - Recommendations the 5 most frequently checked-out books + 5 random books not already recommended.
- **Responses**:
    - `200 OK` → returns a list of `Book` objects
    - `500 Internal Server Error` → `"Error occurred when getting all recommended books"`

---

### Add a Copy
**PATCH** `/book/{bookId}/add`

- **Path Parameter**:
    - `bookId` (integer): unique identifier of the book
- **Description**: Increases the number of copies for the given book.
- **Responses**:
    - `200 OK` → returns the updated `Book` object
    - `404 Not Found` → `"Book not found."`
    - `500 Internal Server Error` → `"Error occurred when adding a copy."`

---

### Checkout a Book
**PATCH** `/book/checkout?bookId={id}`

- **Query Parameter**:
    - `bookId` (integer): unique identifier of the book
- **Description**: Attempts to check out a copy of the book if available.
- **Responses**:
    - `200 OK` → returns the updated `Book` object
    - `404 Not Found` → `"Book not found."`
    - `409 Conflict` → book unavailable (no copies left)
    - `500 Internal Server Error` → `"Error occurred when adding a copy."` (note: message could be refined)

---