package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Assigns data to the List View child items
     * @param sandwich
     */
    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownAs = findViewById(R.id.also_known_tv_value);
        TextView placeOfOrigin = findViewById(R.id.origin_tv_value);
        TextView description = findViewById(R.id.description_tv_value);
        TextView ingredients = findViewById(R.id.ingredients_tv_value);

        StringBuilder otherNames = formatSandwichDataListItems(sandwich.getAlsoKnownAs());
        alsoKnownAs.setText(handleNoDataForViews(otherNames.toString()));
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        description.setText(sandwich.getDescription());
        StringBuilder ingredientList = formatSandwichDataListItems(sandwich.getIngredients());
        ingredients.setText(handleNoDataForViews(ingredientList.toString()));

    }


    /**
     * This method handles the case where no Data is available
     */
    private String handleNoDataForViews(String list){
        return list.equals("") ? getString(R.string.detail_error_message) : list;
    }

    /**
     * Helper method to format list
     */
    private StringBuilder formatSandwichDataListItems(List<String> data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : data){
            stringBuilder.append(" - " + string + "\n");
        }
        return stringBuilder;
    }
}
