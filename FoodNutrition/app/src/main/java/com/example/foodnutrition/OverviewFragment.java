package com.example.foodnutrition;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class OverviewFragment extends Fragment {

    private static final int MAX_NUMBER_DISH = 30;
    private DishAdapter dishAdapter;

    private OnItemClickListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        listener = (MainActivity) getActivity();

        if(dishAdapter == null) {
            dishAdapter = new DishAdapter(listener);
            refreshDishes();
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean random = prefs.getBoolean("random_recipe", true);

        TextView overviewModeText = view.findViewById(R.id.overviewModeText);

        if(random) {
            overviewModeText.setText("Mode: Random");
        } else {
            overviewModeText.setText("Mode: ComplexSearch");
        }

        ImageButton refreshButton = view.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(v -> refreshDishes());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(dishAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void refreshDishes(){
        dishAdapter.removeAllDishes();
        RequestData();
        dishAdapter.notifyDataSetChanged();
    }

    private void RequestData() {
        String apiBaseUrl = getString(R.string.api_base_url);

        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            boolean random = prefs.getBoolean("random_recipe", true);

            if (random) {
                new ApiRequest().execute(apiBaseUrl + "random?number=" + MAX_NUMBER_DISH);
                return;
            }

            new ApiRequest().execute(apiBaseUrl + "complexSearch?" + buildParam(prefs) + "addRecipeInformation=true&number=" + MAX_NUMBER_DISH);

        } catch (Exception e) {
            new ApiRequest().execute(apiBaseUrl + "random?number=" + MAX_NUMBER_DISH);
        }
    }

    private String buildParam(SharedPreferences prefs){
        String params = "";
        String recipe = prefs.getString("recipe_text", "");

        if(recipe != "") {
            params += "query=" + recipe + "&";
        }

        String cuisine = prefs.getString("cuisine_text", "");

        if(cuisine != "") {
            params += "cuisine=" + cuisine + "&";
        }

        return params;
    }

    private class ApiRequest extends AsyncTask<String, Integer, JSONObject> {

        private ArrayList<JSONObject> dataResponses;

        public ApiRequest() {
            dataResponses = new ArrayList<>();
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            for (String url : urls) {
                try {
                    HttpURLConnection connection = this.AttemptConnection(url);

                    dataResponses.add(this.GetJSONData(connection));

                    connection.disconnect();

                } catch (Exception e) {

                }
            }

            return dataResponses.get(0);
        }

        protected void onPostExecute(JSONObject result) {

            try {
                Object recipes = null;
                if(result.has("recipes")) recipes = result.get("recipes");
                if(result.has("results")) recipes = result.get("results");

                if (recipes instanceof JSONArray) {
                    JSONArray recipesArray = (JSONArray) recipes;
                    for (int i = 0; i < recipesArray.length(); i++) {
                        JSONObject recipeJson = recipesArray.getJSONObject(i);
                        dishAdapter.addDish(new Dish(recipeJson));
                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }

        private HttpURLConnection AttemptConnection(String urlString) throws IOException {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-api-key", "ffb4b0dbeae8467e88fb6cb9cab493da");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return connection;
            }
            return null;
        }

        private JSONObject GetJSONData(HttpURLConnection connection) throws IOException, JSONException {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }

            br.close();

            return new JSONObject(sb.toString());
        }

    }
}