package data;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Julien on 23/10/2016.
 * Classe permettant d'offrir des méthodes d'accès aux tables de données des livres sur
 * le site de notre application.
 */

public class BookHttpClient {



    /**
     * Méthode envoyant une requête HTTP via "POST"
     * @param url
     * @param params
     */
    public String sendPost(String url, String params){


        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            conn.setFixedLengthStreamingMode(params.getBytes().length);

            conn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            conn.setRequestProperty("Accept","*/*");


            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();


            // On veut lire la réponse du serveur
            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + params);
            System.out.println("Response Code : " + responseCode);

            if(responseCode == 404 ){

                conn.getErrorStream();
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            System.out.println("Réponse du serveur : " + response.toString());
            in.close();
            conn.disconnect();

            return  Integer.toString(responseCode);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return "Requête non concluante";
    }

    /**
     * Méthode permettant d'obtenir le code réponse du requête url via la méthode GET
     * @param url
     * @return
     */
    public String getResponseCodeFor(String url){

        try {
            HttpURLConnection connection;
            InputStream inputStream;
            StringBuffer stringBuffer = new StringBuffer();

            // On définit la connection et on l'ouvre
            connection = (HttpURLConnection) (new URL(url)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.connect();

            // Print la réponse
            int responseCode = connection.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            connection.disconnect();

            return Integer.toString(responseCode);


        } catch (IOException e) {

            e.printStackTrace();
        }
        return "requête non concluante";
    }

    /**
     * Méthode permettant de demander une ressource via la méthode GET de HTTP
     * @param url
     * @return
     */
    public String sendGet(String url){



        try {
            HttpURLConnection connection;
            InputStream inputStream;
            StringBuffer stringBuffer = new StringBuffer();

            // On définit la connection et on l'ouvre
            connection = (HttpURLConnection) (new URL(url)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.connect();

            // Print la réponse
            int responseCode = connection.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            //On lit la réponse si il y'en a une

            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\r\n");
            }

            inputStream.close();
            connection.disconnect();

            return stringBuffer.toString();


        } catch (IOException e) {

            e.printStackTrace();
        }
        return "requête non concluante";

    }

    /**
     * Méthode pour supprimer une ressource de la BD du serveur.
     * @param url
     * @return
     */
    public String sendDelete(String url){

        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestMethod("DELETE");
            System.out.println(conn.getResponseCode());
            return Integer.toString(conn.getResponseCode());
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return "Requête non concluante";
    }

}
