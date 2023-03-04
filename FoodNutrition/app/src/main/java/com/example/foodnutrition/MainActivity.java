package com.example.foodnutrition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;
import android.util.Log;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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

        new ApiRequest().execute("https://api.spoonacular.com/recipes/random");


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

    private class ApiRequest extends AsyncTask<String, Integer, JSONObject>  {

        private ArrayList<JSONObject> dataResponses;

        public ApiRequest(){
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

        protected void onPostExecute(JSONObject result){
            TextView textView = findViewById(R.id.textView2);

            textView.setText(result.toString());
        }

        private HttpURLConnection AttemptConnection(String urlString) throws IOException {
            URL url = new URL(urlString + "?number=10");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-api-key", "ffb4b0dbeae8467e88fb6cb9cab493da");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                return connection;
            }
            return null;
        }

        private JSONObject GetJSONData(HttpURLConnection connection) throws IOException, JSONException {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }

            br.close();

            return new JSONObject(sb.toString());
        }

    }
}