# Backend-Developer-Test

## Overview
This is a simple Spring Boot application for managing books and authors in a bookstore. It provides RESTful APIs for creating, updating, retrieving, and deleting books and authors.
## Completion Time
This project was completed on 2025-02-15 at 10:30 PM.

## Features
- CRUD operations for books and authors
- Retrieve a book by ID (basic details by default, optional full author details)
- Retrieve a list of all books with optional filtering by:
  - author ID
  - published date (e.g., books published after a certain date)
  - title (pattern matching)
  - price (greater or less than a specified value)
- Validation for unique ISBNs and positive price values
- Proper error handling (e.g., handling cases where a book is not found)

## Technologies Used
- **Spring Boot** (REST API, JPA, Validation)
- **MySQL** (Relational Database Management System)
- **Lombok** (to reduce boilerplate code)
- **MapStruct** (for DTO mapping)

## Setup and Installation

### Prerequisites
1. Install **IntelliJ IDEA** or **Eclipse**:
   - IntelliJ IDEA: [Download IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
   - Eclipse: [Download Eclipse](https://www.eclipse.org/downloads/)
2. Install **MySQL**:
   - MySQL Community Edition: [Download MySQL](https://dev.mysql.com/downloads/installer/)

### Configuration
1. Clone the repository:
   ```sh
   git clone https://github.com/QuanHuynh123/Backend-Developer-Test.git
   cd bookstore
   ```
2. Set up the database:
   - Update `application.properties` with your MySQL configuration:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/bookstore?createDatabaseIfNotExist=true
     spring.datasource.username=root
     spring.datasource.password=yourpassword
     spring.jpa.hibernate.ddl-auto=update
     ```
3. Build and run the application:
   ```sh
   ./mvnw spring-boot:run  # For Mac/Linux
   mvnw.cmd spring-boot:run  # For Windows
   ```

4. Access the API:
   - Example API endpoint: `http://localhost:8080/books`

## API Documentation
The application provides RESTful APIs for managing books and authors.

### Endpoints
#### Books
- **GET** `/books` - Retrieve all books
- **GET** `/books/{id}` - Retrieve a book by ID
- **GET** `/books?authorId=1` - Retrieve a book by AuthorId = 1
- **GET** `/books?minPrice=1&maxPrice=100` - Retrieve a book by price = 1 -> price = 100
- **POST** `/books/add` - Create a new book
- **PUT** `/books/{id}` - Update an existing book
- **DELETE** `/books/{id}` - Delete a book

#### Authors
- **POST** `/authors/add` - Create a new author
- **DELETE** `/authors/{id}` - Delete an author

## Completion Time
This project was completed on [YYYY-MM-DD] within [X hours/days].


