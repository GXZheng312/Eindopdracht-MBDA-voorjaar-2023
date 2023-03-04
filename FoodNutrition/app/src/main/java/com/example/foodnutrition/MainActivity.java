package com.example.foodnutrition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {


    private OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Set fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new OverviewFragment())
                .commit();

    }
    @Override
    public void onItemClick(Dish dish) {
        Toast.makeText(this, "Item Clicked:" + dish.title, Toast.LENGTH_SHORT).show(); // TODO remove
        this.onDishSelected(dish);
    }

    private void onDishSelected(Dish dish) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("title", dish.title);
        detailFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}