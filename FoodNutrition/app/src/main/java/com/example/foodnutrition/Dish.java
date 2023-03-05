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
        if(dishJson.has("instructions")) {
            instructions = dishJson.getString("instructions"); // random api call heeft wel instructions en complexsearch api call niet... :(
        } else {
            instructions = "no instructions";
        }
    }

    // getters

    public String getTitle() {
        return title;
    }

    public String getInstructions() {
        return instructions;
    }

}
