package com.example.foodnutrition;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.InputStream;

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
        ImageButton shareButton = (ImageButton) view.findViewById(R.id.shareButton);
        shareButton.setOnClickListener(view12 -> shareRecipe(view12));

        ImageButton backgroundChangeButton = (ImageButton) view.findViewById(R.id.backgroundButton);
        backgroundChangeButton.setOnClickListener(buttonView -> selectAndChangeBackground(buttonView));

        saveAgendaButton = view.findViewById(R.id.saveAgendaButton);
        saveAgendaButton.setOnClickListener(view1 -> onSaveInAgenda(view1));

        Bundle argument = getArguments();
        if (argument != null) {
            Dish dish = argument.getParcelable(DISH_PARCEL, Dish.class);
            setDish(dish);
        }

        return view;
    }

    public void selectAndChangeBackground(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 42);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 42 && resultCode == RESULT_OK
                    && null != data) {
                Uri selectedImage = data.getData();

                final InputStream imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                Bitmap myBitmap = BitmapFactory.decodeStream(imageStream);

                TextView instructionsTextView = getView().findViewById(R.id.instructionsTextView);
                instructionsTextView.setBackground(new BitmapDrawable(getResources(), myBitmap));
            } else {
                Toast.makeText(getContext(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void setDish(Dish dish) {
        this.dish = dish;
        DisplaySelectedDish();
    }

    public void onSaveInAgenda(View view) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
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
