package utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Julien on 22/10/2016.
 */

public class JSONUtils {


    public static JSONObject getObject(String tagName, JSONObject jsonObject) throws JSONException {
        JSONObject jObj = jsonObject.getJSONObject(tagName);
        return jObj;
    }


    public static String getString(String tagName, JSONObject jsonObject) throws JSONException{
        return(String) jsonObject.getString(tagName);

    }

    public static float getFloat(String tagName, JSONObject jsonObject) throws JSONException{
        return (float) jsonObject.getDouble(tagName);


    }

    public static double getDouble(String tagName, JSONObject jsonObject) throws JSONException{
        return (double)jsonObject.getDouble(tagName);
    }

    public static int getInt(String tagName, JSONObject jsonObject) throws JSONException{

        return (int) jsonObject.getInt(tagName);
    }
}
