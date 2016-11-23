package utils;

/**
 * Created by Julien on 2016-11-15.
 * classe contenant les constantes String permettant de disposer des URL et des paramètres nécessaire
 * au dialogue avec le serveur de la coopérative.
 */

public class HttpUtils {

    public final static String CODE_OK ="200";

    public final static String SERVER_URL = "http://104.236.210.211:3000/";
    public final static String LAST_COPIE = "copies/last.json";

    // Ex pour récup un book associé à une copie : books/5807184ec8bf97325a533ff7.json
    //On peut maintenant ajouter un livre en faisant /books/add?isbn=*ISBN*
    public final static String BOOK = "book/";
    public final static String BOOKS = "books/";

    public final static String COPIES = "copies/";

    public final static String STUDENT_PARAM ="student_email=";
    public final static String STUDENT_TOKEN_PARAM ="student_token=";
    public final static String STUDENT = "student=";

    public final static String AND = "&";
    public final static String JSON = ".json";


    public final static String COOPMANAGER_EMAIL ="coopmanager_email=";
    public final static String COOPMANAGER_TOKEN ="coopmanager_token=";

    public final static String WAITING_FOR_RECEPTION ="waiting_for_reception";



}
