package com.example.foodnutrition;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DishAdapter extends RecyclerView.Adapter<DishViewHolder> {

    private ArrayList<Dish> dishes;
    private OnItemClickListener listener;

    public DishAdapter(OnItemClickListener listener) {
        this.dishes = new ArrayList<Dish>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_item, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = dishes.get(position);

        holder.titleTextView.setText(dish.title);

        holder.summaryTextView.setText(Html.fromHtml(dish.getSummary(), Html.FROM_HTML_MODE_COMPACT));
        if(dish.imagePath != null){
            holder.imagePath = dish.imagePath;
        }

        holder.loadImage();
        holder.itemView.setOnClickListener(v -> listener.onItemClick(dish));
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
        notifyDataSetChanged();
    }

    public void removeAllDishes() {
        dishes = new ArrayList<Dish>();
    }
}
