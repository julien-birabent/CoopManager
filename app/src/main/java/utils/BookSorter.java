package utils;

import java.util.ArrayList;

import model.Book;

/**
 * Created by Julien on 2016-11-23.
 */

public class BookSorter {


    public BookSorter(){


    }

    /**
     * Méthode permettant de trier une liste de Book selon une expression particulière
     * @param expression : l'expression rechercher dans la description d'un livre
     * @param booksToSort : la liste contenant les Books à trier selon l'expression
     * @return une liste de Book contenant des Book contenant l'expression entrée en paramètre
     */
    public static ArrayList<Book> sortByExpression(String expression, ArrayList<Book> booksToSort){

        ArrayList<Book> booksSorted = new ArrayList<Book>();

        for(Book book:booksToSort){
            String bookDescription = book.toString();
            if(bookDescription.toLowerCase().contains(expression.toLowerCase())){
                booksSorted.add(book);
            }
        }
        return booksSorted;
    }


}
