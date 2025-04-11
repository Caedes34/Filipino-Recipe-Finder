package com.filipinofinder;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import com.filipinofinder.RecipeDetails;

public class Resultspage extends Application {
    
    
    private List<Recipe> passedRecipes; // Declare the passedRecipes variable

    public Resultspage(List<Recipe> passedRecipes) { // Constructor to initialize passedRecipes
        this.passedRecipes = passedRecipes;
    }

    @Override
    public void start(Stage stage) {
        Image icon = new Image(getClass().getResourceAsStream("/com/images/icon.png"));
        stage.getIcons().add(icon);

        // Back and Search buttons
     
        Button searchButton = new Button("New Search");
        HBox topBar = new HBox ( new Region(), searchButton);
        HBox.setHgrow(topBar.getChildren().get(1), Priority.ALWAYS);

        //back button locator for css
        
        searchButton.getStyleClass().add("searchButton"); 

        VBox root = new VBox(15,topBar);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Search Results");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        root.getChildren().add(title);

        if (passedRecipes.isEmpty()) {
            Label noResults = new Label("No recipes found.");
            root.getChildren().add(noResults);
        } else {
            for (Recipe recipe : passedRecipes) {
                HBox recipeCard = createRecipeCard(recipe);
                root.getChildren().add(recipeCard);
            }
        }

          // Wrap the VBox in a ScrollPane to enable scrolling
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToHeight(true);  // Ensures the scroll pane fits the height of the window
        scrollPane.setFitToWidth(true);   // Ensures the scroll pane fits the width of the window

   
        Scene scene = new Scene(scrollPane, 600, 400);
        scene.getRoot().setStyle("-fx-focus-border: transparent; -fx-focus-color: transparent;");
        stage.setTitle("Recipe Results");
        stage.setScene(scene);
        stage.show();

        scene.getStylesheets().add(getClass().getResource("/styles/reciperesults.css").toExternalForm());


        
        searchButton.setOnAction(e -> {
        filipinorecipefinder FilipinoRecipeFinder = new filipinorecipefinder();
        Stage newStage = new Stage();
        FilipinoRecipeFinder.start(newStage);


        Stage currentStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        currentStage.close();
    });



    }


    private HBox createRecipeCard(Recipe recipe) {
        HBox card = new HBox(10);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-border-color: gray; -fx-border-radius: 8px; -fx-background-color: #fdfdfd; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

        // Load the image if path is valid
        ImageView imageView;
        try {
            Image img = new Image("file:" + recipe.getImagePath(), 220, 220, true, true);
            imageView = new ImageView(img);

          Rectangle clip = new Rectangle(220, 220); // match image size
            clip.setArcWidth(30);  // adjust to control corner roundness
            clip.setArcHeight(30);
            imageView.setClip(clip);

        } catch (Exception e) {
            imageView = new ImageView(); // empty if loading fails
        }
        

        

        
        
        VBox details = new VBox(5);
        Label name = new Label("Name: " + recipe.getName());
        Label category = new Label("\u2615Category: " + recipe.getCategory());

        name.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        

       
        Label time = new Label("\u23F0Cooking Time: " + recipe.getCookingTime());
        details.getChildren().addAll(name, category, time);
        
        
        card.getChildren().addAll(imageView, details);

        card.setOnMouseClicked(event ->{
            System.out.println("Clicked" + recipe.getName());
            

            //when card is pressed it will scale down
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), card);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(0.95);
            scaleTransition.setToY(0.95);
            scaleTransition.setCycleCount(1);
            scaleTransition.setAutoReverse(true);
            scaleTransition.play();

            //and when the animation is finished it will scale back to normal size
            scaleTransition.setOnFinished(e ->{
                ScaleTransition returnScale = new ScaleTransition(Duration.millis(200), card);
                returnScale.setFromX(0.95);
                returnScale.setFromY(0.95);
                returnScale.setToX(1);
                returnScale.setToY(1);
                returnScale.play();
            });

            
        // List<Recipe> recipesList;
        // Create an instance of RecipeDetails and pass the recipe's data to it
        RecipeDetails recipeDetails = new RecipeDetails(
            recipe.getName(),
            recipe.getIngredients(),
            recipe.getInstructions(),
            recipe.getImagePath(),
            recipe.getNutritionalInfo(),
            recipe.getSource(),
            recipe.getDescription(),
            recipe.getPrepTime(),
            recipe.getCookingTime(),
            recipe.getCategory(),
            passedRecipes // Pass the list of recipes to RecipeDetails
        );
    //close current window
    Stage currentStage = (Stage) card.getScene().getWindow();
    currentStage.close();
    // Open RecipeDetails window

    Stage detailsStage = new Stage();
    recipeDetails.start(detailsStage);
  


    detailsStage.initOwner(currentStage);
    });
        



        return card;
    }
}
