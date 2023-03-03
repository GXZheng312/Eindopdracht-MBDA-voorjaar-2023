package com.example.foodnutrition;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DishViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;
    public ImageView imageView;

    public DishViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title_text_view);
        imageView = itemView.findViewById(R.id.image_view);
    }

}
