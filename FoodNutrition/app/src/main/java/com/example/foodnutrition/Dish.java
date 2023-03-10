package com.example.foodnutrition;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint("ParcelCreator")
public class Dish implements Parcelable {
    protected String title;
    protected String imagePath;
    protected String instructions;
    protected String summary;

    public Dish(JSONObject dishJson) throws JSONException {
        title = dishJson.getString("title");
        if(dishJson.has("image")) {
            imagePath = dishJson.getString("image");
        }
        if(dishJson.has("instructions")) {
            instructions = dishJson.getString("instructions");
        } else {
            if(dishJson.has("summary")) {
                instructions = dishJson.getString("summary");
            } else {
                instructions = "no instructions";
            }
        }

        if(dishJson.has("summary")) {
            summary = dishJson.getString("summary");
        } else {
            summary = "no summary";
        }
    }

    // getters

    public String getTitle() {
        return title;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getSummary() {
        return summary;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(instructions);
        parcel.writeString(imagePath);
        parcel.writeString(title);
        parcel.writeString(summary);
    }
}
