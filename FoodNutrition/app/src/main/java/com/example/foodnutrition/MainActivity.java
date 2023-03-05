package com.example.foodnutrition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements OnItemClickListener {


    private OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheme();
        openFragment(new OverviewFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_menu, menu);
        MenuItem settingsMenuItem = menu.findItem(R.id.toolbar_settings);

        settingsMenuItem.setOnMenuItemClickListener(menuItem -> {
            openFragment(new SettingsFragment());
            return true;
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
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

        openFragment(detailFragment);
    }

    private void setTheme(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = preferences.getString("background_color", "light");
        Log.d("my", "setTheme: " + theme);
        if (theme.equals("dark")) {
            setTheme(R.style.Theme_FoodNutrition_dark);
        } else {
            setTheme(R.style.LightTheme);
        }
    }

}