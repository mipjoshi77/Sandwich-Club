package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {

            JSONObject baseJSONRespone = new JSONObject(json);

            JSONObject name = baseJSONRespone.getJSONObject("name");

            String mainName = name.optString("mainName");

            JSONArray alsoKnownAs = name.optJSONArray("alsoKnownAs");

            ArrayList<String> otherNames = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                otherNames.add(alsoKnownAs.getString(i));
            }

            String placeOfOrigin = baseJSONRespone.getString("placeOfOrigin");
            String description = baseJSONRespone.getString("description");
            String image = baseJSONRespone.getString("image");

            JSONArray ingredients = baseJSONRespone.getJSONArray("ingredients");
            ArrayList<String> sandwichIngredients = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                sandwichIngredients.add(ingredients.getString(i));
            }

            return new Sandwich(mainName, otherNames, placeOfOrigin, description, image, sandwichIngredients);

        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
