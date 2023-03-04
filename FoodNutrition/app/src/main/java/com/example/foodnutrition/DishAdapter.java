package com.example.foodnutrition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DishAdapter extends RecyclerView.Adapter<DishViewHolder> {

    private ArrayList<Dish> dishes;
    private OnItemClickListener listener;

    public DishAdapter(ArrayList<Dish> dishes, OnItemClickListener listener) {
        this.dishes = dishes;
        this.listener = listener;
    }

    public DishAdapter(OnItemClickListener listener) {
        this.dishes = new ArrayList<Dish>();
        this.listener = listener;
    }



    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
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
//        holder.imageView.setImageResource(dish.id)
        holder.titleTextView.setText(dish.title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(dish);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
        notifyDataSetChanged();
    }
}
