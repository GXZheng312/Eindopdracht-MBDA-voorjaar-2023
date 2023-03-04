package com.example.foodnutrition;

public class Dish {
    protected String title;
    protected String instructions;

    public Dish(String title, String instructions) {
        this.title = title;
        this.instructions = instructions;
    }

    // getters

    public String getTitle() {
        return title;
    }

    public String getInstructions() {
        return instructions;
    }

}
