package com.filipinofinder;

import java.lang.classfile.Instruction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.shape.Rectangle;
import javafx.scene.layout.*;

import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.filipinofinder.Recipe; // Adjusted to the correct package path

public class filipinorecipefinder extends Application {

    private Label label1;  //display instructions
    private TextField searchField; //text field para sa user input

    @Override
    public void start(Stage primaryStage) {
        Image icon = new Image(getClass().getResourceAsStream("/com/images/icon.png"));
        primaryStage.getIcons().add(icon);    
    
        //main title label
        Label title = new Label("Filipino Recipe Finder");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;"); 
        title.setPadding(new Insets(0, 0, 0, 10));

        //load & set logo image
        Image logo = new Image(getClass().getResourceAsStream("/com/images/logo.png"));
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(60);
        logoView.setFitWidth(70);

        //horizontal box para sa logo at title
        HBox titlecontainer = new HBox(2, logoView, title);
        titlecontainer.setAlignment(Pos.CENTER);
        titlecontainer.setPadding(new Insets(10, 10, 5, 0));
    
        //subtitle label
        Label subtitle = new Label("Find delicious Filipino recipes by dish type or ingredients");
        subtitle.setStyle("-fx-font-size: 13px;");
        subtitle.getStyleClass().add("subtitle");   //css style
        subtitle.setPadding(new Insets(0, 0, 0, 125));

        title.getStyleClass().add("Title");
     
        //toggle group para sa search options
        ToggleGroup toggleGroup = new ToggleGroup();

        //toggle buttons for search options
        ToggleButton searchBysurprise = new ToggleButton("Surprise me!");
        ToggleButton searchByCategory = new ToggleButton("Search by Categories");

        searchBysurprise.setToggleGroup(toggleGroup);
        
        searchByCategory.setToggleGroup(toggleGroup);
     

        //css para sa toggle buttons
        searchBysurprise.getStyleClass().add("search-toggle");
        searchByCategory.getStyleClass().add("search-toggle");
        
        
       
        HBox toggleBox = new HBox(20, searchByCategory, searchBysurprise);
        toggleBox.setAlignment(Pos.CENTER);
        toggleBox.setPadding(new Insets(10, 10, 10, 80));
        toggleBox.setPrefSize(30, 30);

        toggleBox.getStyleClass().add("toggle-container");

        
    
        label1 = new Label("Enter Dish name or ingredients(eg., Adobo, Sinigang, Pancit, Fried Chicken, salt, flour): ");
        label1.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
        searchField = new TextField();
        searchField.setPromptText("Search....");
        searchField.setPrefWidth(450);
        searchField.setMaxWidth(Double.MAX_VALUE);

        searchField.getStyleClass().add("searchField");


        Button searchButton = new Button("🔍 Search");

        searchButton.setPrefWidth(100);
        searchButton.setMaxWidth(Double.MAX_VALUE);


        // searc
        searchButton.getStyleClass().add("searchbutton");
        

        HBox searchBox = new HBox(10,label1 ,searchField, searchButton);
        //top,right,bottom,left
        searchBox.setPadding(new Insets(1, 10, 10, 10));
        searchBox.setAlignment(Pos.CENTER_LEFT);

        
        // Popular searches
        Label popularLabel = new Label("Popular Searches:");
        popularLabel.getStyleClass().add("popularlabel");
        HBox popularSearches = new HBox(10,
            new Button("Adobo"),
            new Button("Sinigang"),
            new Button("Pancit"),
            new Button("Lumpia"),
            new Button("Kare-kare"),
            new Button("Lechon")
        );

        VBox popularContainer = new VBox(5, popularLabel,popularSearches);
        popularContainer.setPadding(new Insets(5, 10, 5, 10));
        popularLabel.setPadding(new Insets(1,450,0,0));
        popularContainer.setAlignment(Pos.BOTTOM_CENTER);

        popularContainer.getStyleClass().add("popularcontainer");


        VBox root = new VBox(10, titlecontainer, subtitle, toggleBox, label1,searchBox, popularContainer);
        root.setPadding(new Insets(15));
        Scene scene = new Scene(root, 600, 340);

        scene.getStylesheets().add(getClass().getResource("/styles/FilipinoRecipe.css").toExternalForm());
     
        primaryStage.setTitle("Filipino Recipe Finder");
        primaryStage.setScene(scene);
        
        primaryStage.show();
        primaryStage.setResizable(false);
        

        searchButton.setOnAction(e -> searchDish(primaryStage));
        searchField.setOnAction(e -> searchDish(primaryStage));


        searchByCategory.setOnAction(e -> {
            try {
                Categories categoriesPage = new Categories();
                Stage categoryStage = new Stage();
                categoriesPage.start(categoryStage);
                primaryStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        searchBysurprise.setOnAction(e -> {
        
                 Recipe random = getRandomRecipe();
            
                if (random != null) {
                    List<Recipe> randomList = new ArrayList<>();
                    randomList.add(random);
                    showResultsPage(randomList);
                    primaryStage.close();
                } else {
                    showAlert("Random Recipe", "No recipe found in the database.");
                }
            
            
            
        });
    }

    private void searchDish(Stage primaryStage){
        String recipeName = searchField.getText().trim();
        if (recipeName.isEmpty()) {
            showAlert("Input Error", "Please enter the name of the recipe.");
            return;
        }

                String url = "jdbc:sqlite:C:/Program Projects/RECIPE FINDER JAVA PROGJECT/my datas.db";
                String  sql = "SELECT * FROM recipeDB WHERE \"Recipe Name\" LIKE ? OR \"Category\" LIKE ? OR \"Ingredients\" LIKE ?";
                
               
              
        
                   try (Connection connection = DriverManager.getConnection(url);
                     PreparedStatement statement = connection.prepareStatement(sql)){
                    
                     statement.setString(1, "%" + recipeName.toLowerCase() + "%");
                     statement.setString(2, "%" + recipeName.toLowerCase() + "%");
                     statement.setString(3, "%" + recipeName.toLowerCase() + "%");
                     ResultSet result = statement.executeQuery();
                    
                        

                    List<Recipe> recipes = new ArrayList<>();
                     while(result.next()) {
                        // declare the variable and assign the value from the database
                        String recipename = result.getString("Recipe Name");
                        String cooktime = result.getString("Cooking time");
                        String category = result.getString("Category");
                        String imagepath = result.getString("imagePath");
                        String ingredients = result.getString("Ingredients");
                        String instructions = result.getString("instructions");
                        String nutritionalinfo = result.getString("nutritional");
                        String source = result.getString("Recipe Source");
                        String description = result.getString("Recipe Description");
                        String prepTime = result.getString("Preparation time");

                        recipes.add(new Recipe(recipename,ingredients,instructions,cooktime,category,imagepath,prepTime,nutritionalinfo,source,description));


                        System.out.println("Found recipe: " + recipename);

                    }

                    if(!recipes.isEmpty()){
                        showResultsPage(recipes);
                      
                        primaryStage.close();
                    }else{
                        showAlert("NO RESULTS", "NO RECIPES FOUND MATCHING YOUR SEARCH");
            
                    }
                    

                    } catch (SQLException e) {

                    System.out.println("Error sql database");
                    e.printStackTrace();
            }     
    }


    private Recipe getRandomRecipe() {
        Recipe recipe = null;
        String url = "jdbc:sqlite:C:/Program Projects/RECIPE FINDER JAVA PROGJECT/my datas.db";
        String sql = "SELECT * FROM recipeDB ORDER BY RANDOM() LIMIT 1";
    
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {
    
            if (result.next()) {
                String recipename = result.getString("Recipe Name");
                String cooktime = result.getString("Cooking time");
                String category = result.getString("Category");
                String imagepath = result.getString("imagePath");
                String ingredients = result.getString("Ingredients");
                String instructions = result.getString("instructions");
                String nutritionalinfo = result.getString("nutritional");
                String source = result.getString("Recipe Source");
                String description = result.getString("Recipe Description");
                String prepTime = result.getString("Preparation time");
    
                recipe = new Recipe(recipename, ingredients, instructions, cooktime, category, imagepath, prepTime, nutritionalinfo, source, description);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return recipe;
    }
    
    private void showResultsPage(List<Recipe> recipes) {
        Resultspage resultsPage = new Resultspage(recipes); // Pass the list of recipes
        Stage resultsStage = new Stage();
        resultsPage.start(resultsStage);
    }
    
   
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
