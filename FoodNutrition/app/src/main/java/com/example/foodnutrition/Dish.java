package com.example.foodnutrition;

import org.json.JSONException;
import org.json.JSONObject;

public class Dish {
    protected String title;
    protected String instructions;

    public Dish(String title, String instructions) {
        this.title = title;
        this.instructions = instructions;
    }

    public Dish(JSONObject dishJson) throws JSONException {
        title = dishJson.getString("title");
        instructions = dishJson.getString("instructions");
    }

    // getters

    public String getTitle() {
        return title;
    }

    public String getInstructions() {
        return instructions;
    }

}
