package com.filipinofinder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.VoiceStatus;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Categories extends Application {
    @Override
    public void start(Stage primaryStage) {
        
        // Set the icon for the window
        Image icon = new Image(getClass().getResourceAsStream("/com/images/icon.png"));
        primaryStage.getIcons().add(icon);

        // EXPLORE CATEGORIES TITLE
        Label title = new Label("\uD83C\uDF74Explore Categories");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        title.setPadding(new Insets(10, 0, 0, 0));

        // Subtitle
        Label subtitle = new Label("Find delicious Filipino recipes by category");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");



        // Back Button
        Button backButton = new Button("← Go Back");
        backButton.getStyleClass().add("backButton");
        //BACK BUTTON LOGIC
        // when pressed it goes to the filipinorecipefinder class 
        backButton.setOnAction(e -> {
            filipinorecipefinder finder = new filipinorecipefinder();
            Stage newStage = new Stage();
            finder.start(newStage);
            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
        });

        //back button container
        HBox topbar = new HBox(backButton);
        topbar.setAlignment(Pos.CENTER_LEFT);
        topbar.setPadding(new Insets(10, 0, 0, 10));

       //category vertical container 
      VBox categoriesBox1 = new VBox(10,
      createCategoryButton("Adobo", "chicken_adobo.jpg"),
      createCategoryButton("Bread", "Banana Bread with Raisins.jpg")
      );

         //category vertical container 
      VBox categoriesBox2 = new VBox(10,
      createCategoryButton("Chicken", "Pineapple Fried Chicken.jpg"),
      createCategoryButton("Kaldereta", "Ground Pork Kaldereta Omelet.jpg")
      );

         //category vertical container 
      VBox categoriesBox3 = new VBox(10,
      createCategoryButton("Ginataan", "Ginataang-Bitsuelas.jpg"),
      createCategoryButton("Kilawin", "Kinilaw-na-Tanigue.jpg")
      );

        

        // Category containers place horizontally
        HBox categoriesBox = new HBox(20,
            
            categoriesBox1,
            categoriesBox2,
            categoriesBox3
        );
        categoriesBox.setAlignment(Pos.CENTER);
        categoriesBox.setPadding(new Insets(20));

        // Layout of the whole page
        VBox root = new VBox(15,
            topbar,
            title,
            subtitle,
            categoriesBox
        );
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));
        root.getStyleClass().add("categoriesRoot");

        // window height and width
        Scene scene = new Scene(root, 900, 700);
        //connect css file
        scene.getStylesheets().add(getClass().getResource("/styles/categories.css").toExternalForm());

        //title for the Window
        primaryStage.setTitle("Categories");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    //logic for the category buttons
    //when pressed it will search the database for the category name and show the results
    //and close the current window

    private Button createCategoryButton(String categoryName, String imagePath) {
        Image image = new Image(getClass().getResource("/com/images/" + imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(190);
        imageView.setFitHeight(190);

        //category buttons
        Button button = new Button(categoryName, imageView);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
        button.getStyleClass().add("category-button");

        //category buttons logic
        //when pressed it will search the database for the category name and show the results
        //and close the current window

        button.setOnAction(e -> {
            searchDish(categoryName);
            //close this window
            Stage currentStage = (Stage) button.getScene().getWindow();
            currentStage.close();
        });
        return button;
    }
    //search dish logic
    //the logic responsible for fetching recipes based on category

    private void searchDish(String categoryName) {
        String url = "jdbc:sqlite:C:/Program Projects/RECIPE FINDER JAVA PROGJECT/my datas.db";
        String sql = "SELECT * FROM recipeDB WHERE \"Category\" LIKE ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + categoryName.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            List<Recipe> recipes = new ArrayList<>();
            while (rs.next()) {
                recipes.add(new Recipe(
                    rs.getString("Recipe Name"),
                    rs.getString("Ingredients"),
                    rs.getString("instructions"),
                    rs.getString("Cooking time"),
                    rs.getString("Category"),
                    rs.getString("imagePath"),
                    rs.getString("Preparation time"),
                    rs.getString("nutritional"),
                    rs.getString("Recipe Source"),
                    rs.getString("Recipe Description")
                ));
                System.out.println("Found recipe: " + rs.getString("Recipe Name"));
            }

            if (!recipes.isEmpty()) {
                showResultsPage(recipes);
            } else {
                showAlert("No Results", "No recipes found for this category.");
            }

        } catch (SQLException e) {
            System.err.println("Database error:");
            e.printStackTrace();
        }
    }
    /// Show results page with the fetched recipes
    /// This method creates a new stage and displays the results using the Resultspage class.
    /// It passes the list of recipes to the Resultspage class for rendering.
    private void showResultsPage(List<Recipe> recipes) {
        Resultspage resultsPage = new Resultspage(recipes);
        Stage resultsStage = new Stage();
        resultsPage.start(resultsStage);
        
    }
    /// Show an alert dialog with a title and message
    /// This method creates an alert dialog with the specified title and message.
    /// It is used to inform the user about the results of their actions.
    /// 
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
