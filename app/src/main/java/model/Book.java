package model;

import java.util.ArrayList;

/**
 * Created by Julien on 22/10/2016.
 */

public class Book {

    private String bookId;
    private String isbn;
    private String author;
    private String title;
    private String price;
    private String nbPages;

    private Copy copy;

    public Book(){

    }

    public Book(String isbn, String author, String title, String price, String nbPages, Copy copy) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.price = price;
        this.nbPages = nbPages;
        this.copy = copy;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", nbPages='" + nbPages + '\'' +
                getCopy().getPhysicalState() +
                '}';
    }

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNbPages() {
        return nbPages;
    }

    public void setNbPages(String nbPages) {
        this.nbPages = nbPages;
    }
}
