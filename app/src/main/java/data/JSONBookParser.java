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


    public JSONBookParser() {
    }

    /**
     * Méthode pour décomposer une string représentant un objet JSON en un objet Book.
     * @param contents
     * @return
     */
    public static Book parseBook(String contents)
    {
        Book book = new Book();

        try {

            // On convertit la chaîne de caractère représentant l'objetJSON en objet JSON
            JSONObject objBook = new JSONObject(contents);
            // On remplit les champs de l'instance de Book avant de la retourner
            book.setAuthor(JSONUtils.getString("author", objBook));
            book.setTitle(JSONUtils.getString("title",objBook));
            book.setIsbn(JSONUtils.getInt("isbn",objBook));
            book.setNbPages(JSONUtils.getInt("page_count",objBook));
            book.setPrice(JSONUtils.getFloat("mint_price",objBook));

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
    public static ArrayList<Book> parseManyBooks(String contents){

        ArrayList<Book> bookArrayList = new ArrayList<Book>();
        try {
            // on récupère le tableau JSON de Book
            JSONArray booksArray = new JSONArray(contents);
            // Pour chaque description de Book, on créé un Book physique
            for(int i =0; i<booksArray.length();i++){
                parseBook(booksArray.get(i).toString());
            }

            return bookArrayList;
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return bookArrayList;
    }

}
