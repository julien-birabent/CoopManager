package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.Copy;
import utils.JSONUtils;

/**
 * Created by Julien on 2016-11-16.
 */

public class JSONCopyParser {


    public final static String BOOK_ID = "book_id";
    public final static String COPY_ID = "id";
    public final static String INTEGRITY ="integrity";
    public final static String ID = String.valueOf('$')+"oid";
    public final static String AVAILABILITY = "availability";

    public JSONCopyParser() {

    }

    /**
     * Méthode permettant de parse une String représentant un objet JSON en un objet du modèle Copy
     * @return
     */
    public Copy parseCopy(String contents){

        //System.out.println("CopyParser : String entrée : " + contents);

        Copy copy = new Copy();
        try {
            // On convertit la chaîne de caractère représentant l'objet JSON en objet JSON
            JSONObject objCopy = new JSONObject(contents);

            // On récupère l'objet JSON contenant l'id du Book associée à la copy.
            JSONObject objBookId = JSONUtils.getObject(BOOK_ID,objCopy);

            // On attribut l'id du Book associé à Copy
            String bookIdString = JSONUtils.getString(ID,objBookId);
            copy.setBookId(bookIdString);

            // On récupère l'objet JSON contenant l'id de la Copy
            JSONObject objCopyId = JSONUtils.getObject(COPY_ID,objCopy);
            // On extrait l'id de la Copy contenu dans l'objet précédemment extrait.
            copy.setCopyId(JSONUtils.getString(ID,objCopyId));

            copy.setPhysicalState(JSONUtils.getString(INTEGRITY,objCopy));
            copy.setAvailability(JSONUtils.getString(AVAILABILITY,objCopy));
            return copy;

        }
        catch(Exception e ){

        }

        return copy;
    }

    /**
     * Méthode permettant de parse un objet JSON représentant une Copy en un objet du modèle Copy
     * @return
     */
    public Copy parseCopy(JSONObject data){

        //System.out.println("CopyParser : String entrée : " + contents);

        Copy copy = new Copy();
        try {
            // On convertit la chaîne de caractère représentant l'objet JSON en objet JSON
            // System.out.println("String brute convertie en JSON" + objCopy.toString());


            // On récupère l'objet JSON contenant l'id du Book associée à la copy.
            JSONObject objBookId = JSONUtils.getObject(BOOK_ID,data);
            // System.out.println("JSON contenant book_id : " + objBookId.toString());

            // On attribut l'id du Book associé à Copy
            String bookIdString = JSONUtils.getString(ID,objBookId);
            //System.out.println(bookIdString);
            copy.setBookId(bookIdString);
            //System.out.println(" id contenu dans book_id : " + copy.getBookId());



            // On récupère l'objet JSON contenant l'id de la Copy
            JSONObject objCopyId = JSONUtils.getObject(COPY_ID,data);
            // On extrait l'id de la Copy contenu dans l'objet précédemment extrait.
            copy.setCopyId(JSONUtils.getString(ID,objCopyId));

            copy.setPhysicalState(JSONUtils.getString(INTEGRITY,data));
            return copy;

        }
        catch(Exception e ){

        }

        return copy;
    }

    /**
     * Méthode permettant de parse une String représentant un tableau d'objets JSON contenant les
     * descriptions d'objets du package model Copy.
     * @return
     */
    public ArrayList<Copy> parseManyCopies(String contents){

        ArrayList<Copy> copyArrayList = new ArrayList<Copy>();

        try {
            // on récupère le tableau JSON de Book
            JSONArray copiesArray = new JSONArray(contents);

            // Pour chaque description de copy, on créé une copy physique
            for(int i =0; i<copiesArray.length();i++){
                copyArrayList.add(parseCopy(copiesArray.getJSONObject(i)));
            }
            return copyArrayList;
        }
        catch (JSONException e){
            e.printStackTrace();
        }


        return copyArrayList;
    }

}
