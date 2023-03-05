package com.example.foodnutrition;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {

    Dish dish;
    public static final String DISH_PARCEL = "dish";

    TextView titleTextView;
    TextView instructionTextView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        titleTextView = view.findViewById(R.id.titleTextView);
        instructionTextView = view.findViewById(R.id.instructionsTextView);
        ImageButton button = (ImageButton) view.findViewById(R.id.shareButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                shareRecipe(view);
            }
        });


        Toolbar shareToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.setSupportActionBar(shareToolbar);

        Bundle argument = getArguments();
        if(argument != null) {
            Dish dish = argument.getParcelable(DISH_PARCEL, Dish.class);
            setDish(dish);
        }

        return view;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
        DisplaySelectedDish();
    }

    private void DisplaySelectedDish() {
        if (dish == null) {
            return;
        }
        titleTextView.setText(dish.getTitle());
        instructionTextView.setText(Html.fromHtml(dish.getInstructions(), Html.FROM_HTML_MODE_COMPACT));

    }

    public void shareRecipe(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Here is the recipe to make " + dish.getTitle() + " ! " + Html.fromHtml(dish.getInstructions()));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Recipe for " + dish.getTitle());
        Intent shareIntent = Intent.createChooser(intent, "Share");
        startActivity(shareIntent);
    }
}
