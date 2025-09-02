package com.aurionpro.book.model;

import java.util.Comparator;

public class ComapareByTitle implements Comparator<Book> {

	@Override
	public int compare(Book book1, Book book2) {
	    return book2.getBookTitle().compareTo(book1.getBookTitle()); // Reverse order
	}


}
