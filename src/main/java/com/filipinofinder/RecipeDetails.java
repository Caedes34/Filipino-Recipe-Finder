package com.filipinofinder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.awt.Desktop;

import com.filipinofinder.Resultspage;

public class RecipeDetails extends Application {
    private String  recipeName;
    private String  ingredients;
    private String  instructions;
    private String  imagepath;
    private String  nutritionalinfo;
    private String  source;
    private String  description;
    private String  preptime;
    private String  cooktime;
    private String  category;
    private List<Recipe> passedRecipes; // Declare the passedRecipes field

    
   
    public RecipeDetails(String recipeName, String ingredients, String instructions, String imagePath, String nutritionalinfo, String source, String description, String preptime, String cooktime, String category, List<Recipe> recipes) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.imagepath = imagePath;
        this.nutritionalinfo = nutritionalinfo;
        this.source = source;
        this.description = description;
        this.preptime = preptime;
        this.cooktime = cooktime;
        this.category = category;
        this.passedRecipes = recipes; // Initialize the passed recipes list
    }

    @Override
    public void start(Stage primaryStage) {
        //icon
        Image icon = new Image(getClass().getResourceAsStream("/com/images/icon.png"));
        primaryStage.getIcons().add(icon);  

        // Back and Search buttons
        Button backButton = new Button("← Back to Results");
        Button searchButton = new Button("New Search");
        HBox topBar = new HBox(backButton, new Region(), searchButton);
        HBox.setHgrow(topBar.getChildren().get(1), Priority.ALWAYS);

        //back button locator for css
        backButton.getStyleClass().add("backButton");
        searchButton.getStyleClass().add("searchButton"); 
        
        // Recipe Image and Title
        ImageView recipeImage = new ImageView(new Image("file:" + imagepath));
        recipeImage.setFitHeight(400);
        recipeImage.setFitWidth(400);
        recipeImage.setPreserveRatio(true);

        // Create a Rectangle with rounded corners
        Rectangle clip = new Rectangle(
            recipeImage.getFitWidth(),
            recipeImage.getFitHeight()
        );
        clip.setArcWidth(30);  // adjust these values for roundness
        clip.setArcHeight(30);

        // Apply the clip to the ImageView
        recipeImage.setClip(clip);
        

        Label title = new Label(recipeName);
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: orange; -fx-font-family: 'Arial'; -fx-padding-left: 20px;");

        
       



        Label description = new Label(this.description);
        description.getStyleClass().add("descriptionLabel");

        Label timeLabel = new Label("⏳Cooking Time: " + cooktime);
        Label servingsLabel = new Label("🍽️Prep Time: " + preptime );
        Label cuisineLabel = new Label("🌍Category: " + category);
       
        //button for the source tab
     
        Button sourceButton = new Button("Open Recipe Source");
        sourceButton.setOnAction(e -> openWebPage(source)); 
        Label sourceLabel = new Label("Recipe Source: " + source);  

        VBox titleContainer = new VBox(title, description, sourceLabel,timeLabel, servingsLabel, cuisineLabel);
        titleContainer.setSpacing(5);
        titleContainer.setAlignment(Pos.CENTER_LEFT);



        HBox titleBox = new HBox(recipeImage, titleContainer);
        titleBox.setSpacing(5);
        titleBox.setAlignment(Pos.CENTER);

    
    // Metadata
      


        
    

        //ingredients tab
        //
        Label ingredients = new Label("Ingredients: ");
        ingredients.setStyle("-fx-font-size: 30px; -fx-font-weight:bold; -fx-text-fill: Orange; -fx-font-family: 'Arial';");
        Label ingredientstext = new Label(this.ingredients);

        ingredientstext.getStyleClass().add("descriptionLabel");
        

        //instructions information
        Label instructionslLabel = new Label("Directions");
        instructionslLabel.setStyle("-fx-font-size: 30px;-fx-font-weight:bold; -fx-text-fill: Orange; -fx-font-family: 'Arial';");
        Label instructionstext = new Label(instructions);

        description.getStyleClass().add("descriptionLabel");

        instructionstext.getStyleClass().add("descriptionLabel");

        //nutritional information
        Label nutritionalLabel = new Label("Nutritional info");
        nutritionalLabel.setStyle("-fx-text-fill: Orange; -fx-font-size: 30px; -fx-font-weight:bold");
        Label nutritionalText = new Label(nutritionalinfo);
        nutritionalText.setStyle("-fx-font-size: 14px; -fx-font-weight:bold; -fx-text-fill: black; -fx-font-family: 'Arial';");    
        
          
       
        nutritionalText.getStyleClass().add("descriptionLabel");

        
        
        // Layout setup
        VBox root = new VBox(10,topBar,titleBox,ingredients, ingredientstext, instructionslLabel, instructionstext, nutritionalLabel, nutritionalText, sourceButton);
        root.setPadding(new Insets(15));

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);  // Ensures the content fits within the width

       
        primaryStage.setScene(new Scene(scrollPane, 1000, 700));
        primaryStage.setTitle("Recipe Details");
        primaryStage.show();


        Scene scene = primaryStage.getScene();
        scene.getStylesheets().add(getClass().getResource("/styles/recipedetails.css").toExternalForm());


    //shuts down the app when x is pressed
        primaryStage.setOnCloseRequest(e -> {
            primaryStage.show();
        });

        searchButton.setOnAction(e -> {
            filipinorecipefinder FilipinoRecipeFinder = new filipinorecipefinder();
            Stage newStage = new Stage();
            FilipinoRecipeFinder.start(newStage);


            Stage currentStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            currentStage.close();
        });

        

        backButton.setOnAction(e -> {
           List<Recipe> passedRecipes = getRecipes();

           showResultsPage(passedRecipes);

           Stage currentStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
           currentStage.close();

        });
    }

   

    private void showResultsPage(List<Recipe> recipes) {
        Resultspage resultsPage = new Resultspage(recipes);
        
        Stage newStage = new Stage();
        resultsPage.start(newStage);
    }
    private List<Recipe> getRecipes() {
        return passedRecipes;  // Return the stored recipes list
    }
        private void openWebPage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }

       
    }

 

        public static void main(String[] args) {
            launch(args);
        }

}