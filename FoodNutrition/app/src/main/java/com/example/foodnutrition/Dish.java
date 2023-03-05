package com.example.foodnutrition;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Dish implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(instructions);
        parcel.writeString(title);
    }
}
