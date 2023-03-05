package com.example.foodnutrition;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class DishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView titleTextView;

    private TextView descriptionTextView;
    private ImageButton toggleImageButton;

    public DishViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title_text_view);
        descriptionTextView = itemView.findViewById(R.id.description_text_view);
        toggleImageButton = itemView.findViewById(R.id.toggle_image_button);
        toggleImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (descriptionTextView.getVisibility() == View.GONE) {
            toggleImageButton.setRotation(180);
            descriptionTextView.setVisibility(View.VISIBLE);
        } else {
            toggleImageButton.setRotation(0);
            descriptionTextView.setVisibility(View.GONE);
        }
    }

}
