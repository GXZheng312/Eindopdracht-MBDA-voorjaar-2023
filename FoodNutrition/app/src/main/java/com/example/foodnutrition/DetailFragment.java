package com.example.foodnutrition;

import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {

    Dish dish;
    public static final String DISH_PARCEL = "dish";
    private static final int PERMISSION_REQUEST_WRITE_CALENDAR = 1;

    TextView titleTextView;
    TextView instructionTextView;
    Button saveAgendaButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        titleTextView = view.findViewById(R.id.titleTextView);
        instructionTextView = view.findViewById(R.id.instructionsTextView);
        saveAgendaButton = view.findViewById(R.id.saveAgendaButton);
//        imageView = view.findViewById(R.id.imageView);

        Bundle argument = getArguments();
        if(argument != null) {
            String dishTitle = argument.getString("title");
//            Dish dish = argument.getParcelable(DISH_PARCEL, Dish.class); //TODO
            Dish dish = new Dish(dishTitle, "todo: show info of dish here!"); // TODO get data from API with this dish title
            setDish(dish);
        }

        saveAgendaButton.setOnClickListener(view1 -> onSaveInAgenda(view1));

        return view;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
        DisplaySelectedDish();
    }

    public void onSaveInAgenda(View view){
        Log.d("my", "onSaveInAgenda: inside");
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            Log.d("my", "onSaveInAgenda: requesting");
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_CALENDAR},
                    PERMISSION_REQUEST_WRITE_CALENDAR);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_WRITE_CALENDAR) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Calendar permission is approved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Calendar permission is denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void DisplaySelectedDish() {
        if (dish == null) {
            return;
        }
        titleTextView.setText(dish.getTitle());
        instructionTextView.setText(dish.getInstructions());
    }

}
