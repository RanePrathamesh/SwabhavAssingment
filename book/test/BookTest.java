package com.aurionpro.book.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.aurionpro.book.model.Book;
import com.aurionpro.book.model.ComaparByAuthor;
import com.aurionpro.book.model.ComapareByTitle;


public class BookTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Book> books = new ArrayList<>();
        // Adding two initial books to the collection
        books.add(new Book(1, "book1", "author1", false, 100.0));
        books.add(new Book(2, "book2", "author2", false, 800.0));
        
        int choice;

        do {
            System.out.println("\n--- Welcome to the Book Store ---");
            System.out.println("1. Dashboard");
            System.out.println("2. Exit");
            choice = scanner.nextInt();

            if (choice == 1) {
                handleDashboard(scanner, books);
            }

        } while (choice != 2);

        System.out.println("Thank you for visiting!");
    }

    private static void handleDashboard(Scanner scanner, List<Book> books) {
        System.out.println("\n--- Dashboard ---");
        System.out.println("1. Add a new book");
        System.out.println("2. Borrow a book");
        System.out.println("3. Show available books");
        System.out.println("4. List borrowed books");
        System.out.println("5. Return a book");
        System.out.print("Choose an option: ");
        int action = scanner.nextInt();

        switch (action) {
            case 1:
                addBook(scanner, books);
                break;
            case 2:
                borrowBook(scanner, books);
                break;
            case 3:
                showBooks(scanner, books, false); // Available books
                break;
            case 4:
                showBooks(scanner, books, true);  // Borrowed books
                break;
            case 5:
                returnBook(scanner, books);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addBook(Scanner scanner, List<Book> books) {
        System.out.println("\n--- Add New Book ---");
        System.out.print("Enter Book Title: ");
        String title = scanner.next();
        System.out.print("Enter Author: ");
        String author = scanner.next();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();

        Book newBook = new Book(books.size() + 1, title, author, false, price);
        books.add(newBook);
        System.out.println("Book added successfully!");
    }

    private static void borrowBook(Scanner scanner, List<Book> books) {
        showBooks(scanner, books, false); // Available books
        System.out.print("Enter Book ID to borrow: ");
        int id = scanner.nextInt();

        Book book = findBookById(books, id);
        if (book != null && !book.isBookIssued()) {
            book.setBookIssued(true);
            System.out.println("You have successfully borrowed the book.");
        } else {
            System.out.println("Book not available or already borrowed.");
        }
    }

    private static void returnBook(Scanner scanner, List<Book> books) {
        showBooks(scanner, books, true); // Borrowed books
        System.out.print("Enter Book ID to return: ");
        int id = scanner.nextInt();

        Book book = findBookById(books, id);
        if (book != null && book.isBookIssued()) {
            book.setBookIssued(false);
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Book not found or wasn't borrowed.");
        }
    }

    private static void showBooks(Scanner scanner, List<Book> books, boolean onlyIssued) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isBookIssued() == onlyIssued) {
                filteredBooks.add(book);
            }
        }

        if (filteredBooks.isEmpty()) {
            System.out.println("No books to display.");
        } else {
            System.out.println("\n--- Book List ---");
            for (Book book : filteredBooks) {
                System.out.println(book);
            }
        }

        if (!onlyIssued) {
            System.out.println("\nWould you like to sort the books?");
            System.out.println("1. Sort by Author (Ascending)");
            System.out.println("2. Sort by Title (Descending)");
            int sortChoice = scanner.nextInt();
            sortBooks(filteredBooks, sortChoice);
        }
    }

    private static void sortBooks(List<Book> books, int sortChoice) {
        switch (sortChoice) {
            case 1:
                Collections.sort(books, new ComaparByAuthor());
                break;
            case 2:
                Collections.sort(books, new ComapareByTitle());
                break;
            default:
                System.out.println("Invalid choice. No sorting applied.");
        }

        System.out.println("\nSorted Book List:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private static Book findBookById(List<Book> books, int id) {
        for (Book book : books) {
            if (book.getBookId() == id) {
                return book;
            }
        }
        return null;
    }
}
