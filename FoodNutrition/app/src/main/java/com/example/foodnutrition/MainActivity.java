package com.example.foodnutrition;

import static com.example.foodnutrition.DetailFragment.DISH_PARCEL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    FragmentManager fragmentManager;

    public MainActivity() {
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheme();
        openFragment(new OverviewFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("my", "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.header_menu, menu);
        MenuItem settingsMenuItem = menu.findItem(R.id.toolbar_settings);

        settingsMenuItem.setOnMenuItemClickListener(menuItem -> {
            openFragment(new SettingsFragment());

            return true;
        });

        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (currentFragment instanceof SettingsFragment) {
            settingsMenuItem.setVisible(false);
            settingsMenuItem.setEnabled(false);
        } else {
            settingsMenuItem.setVisible(true);
            settingsMenuItem.setEnabled(true);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

    private void openFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onItemClick(Dish dish) {
        this.onDishSelected(dish);
    }

    private void onDishSelected(Dish dish) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(DISH_PARCEL, dish);
        detailFragment.setArguments(args);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    private void setTheme() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = preferences.getString("background_color", "light");

        if (theme.equals("dark")) {
            setTheme(R.style.Theme_FoodNutrition_dark);
        } else {
            setTheme(R.style.Theme_FoodNutrition_light);
        }
    }
}