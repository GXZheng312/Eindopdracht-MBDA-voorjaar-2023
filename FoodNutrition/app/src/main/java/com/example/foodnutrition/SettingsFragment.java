package com.example.foodnutrition;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

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
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d("my", "onSharedPreferenceChanged: " + key);
        if (key.equals("background_color")) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String themeStyle = prefs.getString("theme_style", "light");

            if (themeStyle.equals("light")) {
                getActivity().setTheme(R.style.LightTheme);
            } else {
                getActivity().setTheme(R.style.DarkTheme);
            }

            getActivity().recreate();
        }
    }
}