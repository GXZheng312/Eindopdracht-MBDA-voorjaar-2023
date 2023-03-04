package com.example.foodnutrition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
//        imageView = view.findViewById(R.id.imageView);

        Bundle argument = getArguments();
        if(argument != null) {
            String dishTitle = argument.getString("title");
//            Dish dish = argument.getParcelable(DISH_PARCEL, Dish.class); //TODO
            Dish dish = new Dish(dishTitle, "todo: show info of dish here!"); // TODO get data from API with this dish title
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
        instructionTextView.setText(dish.getInstructions());

    }
}
