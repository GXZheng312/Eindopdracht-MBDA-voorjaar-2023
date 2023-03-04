package com.example.foodnutrition;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {

    private DishAdapter dishAdapter;

    public OverviewFragment() {
        // Required empty public constructor
    }

    private OnItemClickListener listener;

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment OverviewFragment.
     */
    public static OverviewFragment newInstance(String param1, String param2) {
        OverviewFragment fragment = new OverviewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        ArrayList<Dish> dishes = new ArrayList<>();

        // Create 20 example dishes
        for (int i = 0; i < 20; i++) {
            dishes.add(new Dish("Pancakes" + i, "Mix flour, eggs, milk, and sugar together. Cook on a pan."));
        }

        listener = (MainActivity) getActivity();

//        DishAdapter dishAdapter = new DishAdapter(dishes, this.listener);
        dishAdapter = new DishAdapter(this.listener);
        new ApiRequest().execute("https://api.spoonacular.com/recipes/random");

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(dishAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
                Object recipes = result.get("recipes");

                if (recipes instanceof JSONArray) {
                    JSONArray recipesArray = (JSONArray) recipes;
                    for (int i = 0; i < recipesArray.length(); i++) {
                        JSONObject recipe = recipesArray.getJSONObject(i);
                        dishAdapter.addDish(new Dish(recipe.getString("title"), recipe.getString("instructions")));
                    }
                }


                //                // Loop over all recipes in a for loop
//                for(Object recipe : recipes.values()) {
//
//                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


//            TextView textView = findViewById(R.id.textView2);
//            textView.setText(result.toString());
        }

        private HttpURLConnection AttemptConnection(String urlString) throws IOException {
            URL url = new URL(urlString + "?number=10");
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