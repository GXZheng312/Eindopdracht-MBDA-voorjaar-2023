package com.example.foodnutrition;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_main, rootKey);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d("my", "onSharedPreferenceChanged: " + key);
        if (key.equals("background_color")) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String themeStyle = prefs.getString("theme_style", "light");

            if (themeStyle.equals("light")) {
                getActivity().setTheme(R.style.Theme_FoodNutrition_light);
            } else {
                getActivity().setTheme(R.style.Theme_FoodNutrition_dark);
            }

            getActivity().recreate();
        }
    }
}