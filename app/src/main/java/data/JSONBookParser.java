package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.Book;
import utils.JSONUtils;

/**
 * Created by Julien on 23/10/2016.
 */

public class JSONBookParser {

    public final static String ID = String.valueOf('$')+"oid";
    public final static String BOOK_ID = "id";
    public final static String AUTHOR = "author";
    public final static String TITLE = "title";
    public final static String ISBN ="isbn";
    public final static String PAGE_COUNT = "page_count";
    public final static String MINT_PRICE = "mint_price";

    public JSONBookParser() {
    }

    /**
     * Méthode pour décomposer une string représentant un objet JSON en un objet Book.
     * @param contents
     * @return
     */
    public  Book parseBook(String contents)
    {
        Book book = new Book();

        try {

            // On convertit la chaîne de caractère représentant l'objet JSON en objet JSON
            JSONObject objBook = new JSONObject(contents);
            // On remplit les champs de l'instance de Book avant de la retourner
            book.setAuthor(JSONUtils.getString(AUTHOR, objBook));
            book.setTitle(JSONUtils.getString(TITLE,objBook));
            book.setIsbn(JSONUtils.getString(ISBN,objBook));
            book.setNbPages(JSONUtils.getString(PAGE_COUNT,objBook));
            book.setPrice(JSONUtils.getString(MINT_PRICE,objBook));
            // On va chercher l'id du livre
            JSONObject objIdBook = JSONUtils.getObject(BOOK_ID,objBook);
            book.setBookId(JSONUtils.getString(ID,objIdBook));

            return book;
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return book;
    }

    /**
     * Méthode pour décomposer une string représentant un ensemble d'objet Book.
     * @param contents
     * @return
     */
    public  ArrayList<Book> parseManyBooks(String contents){

        ArrayList<Book> bookArrayList = new ArrayList<Book>();
        try {
            // on récupère le tableau JSON de Book
            JSONArray booksArray = new JSONArray(contents);
            // Pour chaque description de Book, on créé un Book physique
            for(int i =0; i<booksArray.length();i++){
                bookArrayList.add(parseBook(booksArray.get(i).toString())) ;
            }

            return bookArrayList;
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return bookArrayList;
    }

}
