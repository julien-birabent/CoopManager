package utils;

/**
 * Classe ayant pour but de centraliser la concaténation des urls employées par l'application dans
 * des méthodes static.
 */

public class UrlBuilder {


    /**
     * Utilise les constantes de la classe HttpUtils si tu veux. Sinon code en dure.
     */


    /**
     * Url pour récupérer la liste des copies en attente de réception par la coop
     * @return
     */
    public static String getReceptionListUrl(){

        return null;
    }

    /**
     * Url pour détruite une instance de Copy de la base de données
     * @param copieId : l'id de la copy à supprimer
     * @return
     */
    public static String deleteCopieUrl(String copieId){

        String url = HttpUtils.SERVER_URL + HttpUtils.COPIES
                + HttpUtils.DELETE + copieId;

        return url;
    }

    /**
     * Url pour récupérer la liste des copies réservées
     * @return
     */
    public static String getPickingListUrl(){
        return null;
    }

    /**
     * Url permettant de changer l'attribut availability d'une Copy
     * "en attente de reception" --> "disponible à la coop"
     * @param copieId : la copy à modifier.
     * @return
     */
    public static String receiveCopyUrl(String copieId){
        return null;
    }


}
