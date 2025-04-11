package com.filipinofinder;

public class Recipe {
    private String name;
    private String ingredients;
    private String instructions;
    private String nutritionalInfo;
    private String source;
    private String description;
    private String prepTime;
    private String cookingTime;
    private String category;
    private String imagePath;

    public Recipe(String name, String ingredients, String instructions, String cookingTime, String category, String imagePath, String prepTime, String nutritionalInfo, String source, String description) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.cookingTime = cookingTime;
        this.category = category;
        this.imagePath = imagePath;
        this.prepTime = prepTime;
        this.nutritionalInfo = nutritionalInfo;
        this.source = source;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public String getCategory() {
        return category;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getIngredients() {
        return ingredients;
    }
    public String getInstructions() {
        return instructions;
    }
    public String getNutritionalInfo() {
        return nutritionalInfo;
    }
    public String getSource() {
        return source;
    }
    public String getDescription() {
        return description;
    }
    public String getPrepTime() {
        return prepTime;
    }
}