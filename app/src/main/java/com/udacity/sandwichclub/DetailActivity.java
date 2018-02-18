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
    private static final String BULLET = "· ";
    public static final String ALSO_KNOWN_AS_SEPARATOR = ", ";
    public static final String NEW_LINE = "\n";

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

    private void populateUI(Sandwich sandwich) {
        TextView tvDesc = findViewById(R.id.description_tv);
        tvDesc.setText(sandwich.getDescription());

        TextView tvAka = findViewById(R.id.also_known_tv);
        List<String> aka = sandwich.getAlsoKnownAs();
        String saka = "";
        for(int i =0;i<aka.size();i++){
            if (i==0) {
                saka = aka.get(i);
            }else{
                saka = new StringBuilder()
                        .append(saka)
                        .append(ALSO_KNOWN_AS_SEPARATOR)
                        .append(aka.get(i))
                        .toString();
            }
        }
        tvAka.setText(saka);

        TextView tvOrig = findViewById(R.id.origin_tv);
        tvOrig.setText(sandwich.getPlaceOfOrigin());

        TextView tvIng = findViewById(R.id.ingredients_tv);
        List<String> ing = sandwich.getIngredients();
        String sing = "";
        for(int i =0;i<ing.size();i++){
            if (i==0) {
                sing = new StringBuilder()
                        .append(BULLET)
                        .append(ing.get(i))
                        .toString();
            }else{
                sing = new StringBuilder()
                        .append(sing)
                        .append(NEW_LINE)
                        .append(BULLET)
                        .append(ing.get(i))
                        .toString();
            }
        }
        tvIng.setText(sing);
    }
}
