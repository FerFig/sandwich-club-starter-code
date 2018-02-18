package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String JSON_NAME_OBJ = "name";
    private static final String SW_NAME = "mainName";
    private static final String SW_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String SW_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String SW_DESCRIPTION = "description";
    private static final String SW_IMAGE = "image";
    private static final String SW_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json){
        String swName ="", swImage, swPlaceOfOrigin, swDescription;
        JSONArray jsonKnowAs, jsonIngredients;
        List<String> swKnowAs = new ArrayList<>(), swIngredients = new ArrayList<>();

        try {
            JSONObject jsonMainObject = new JSONObject(json);
            if (jsonMainObject.has(JSON_NAME_OBJ)) {
                JSONObject jsonName = jsonMainObject.getJSONObject(JSON_NAME_OBJ);
                swName = jsonName.optString(SW_NAME);
                jsonKnowAs = jsonName.optJSONArray(SW_ALSO_KNOWN_AS);
                if (jsonKnowAs != null) {
                    for (int j = 0; j < jsonKnowAs.length(); j++) {
                        swKnowAs.add(jsonKnowAs.getString(j));
                    }
                }
            }

            swPlaceOfOrigin = jsonMainObject.optString(SW_PLACE_OF_ORIGIN);

            swDescription = jsonMainObject.optString(SW_DESCRIPTION);

            swImage = jsonMainObject.optString(SW_IMAGE);

            jsonIngredients = jsonMainObject.optJSONArray(SW_INGREDIENTS);
            if (jsonIngredients != null) {
                for (int j = 0; j < jsonIngredients.length(); j++) {
                    swIngredients.add(jsonIngredients.getString(j));
                }
            }

            return new Sandwich(swName, swKnowAs, swPlaceOfOrigin,
                    swDescription, swImage, swIngredients);
        } catch (JSONException e) {
            //e.printStackTrace();
            return null;
        }
    }
}
