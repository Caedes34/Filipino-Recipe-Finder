package com.filipinofinder;

import java.sql.*;
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
import javafx.stage.Stage;

public class Categories extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Window Icon
        Image icon = new Image(getClass().getResourceAsStream("/com/images/icon.png"));
        primaryStage.getIcons().add(icon);

        // Title
        Label title = new Label("🍽️ Explore Categories");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        title.setPadding(new Insets(10, 0, 0, 0));

        // Subtitle
        Label subtitle = new Label("Find delicious Filipino recipes by category");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        // Back Button
        Button backButton = new Button("← Go Back");
        backButton.getStyleClass().add("backButton");
        backButton.setOnAction(e -> {
            filipinorecipefinder finder = new filipinorecipefinder();
            Stage newStage = new Stage();
            finder.start(newStage);
            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
        });

        // Category Buttons
        HBox categoriesBox = new HBox(20,
            createCategoryButton("Adobo", "chicken_adobo.jpg"),
            createCategoryButton("Bread", "Banana Bread with Raisins.jpg"),
            createCategoryButton("Ginataan", "Ginataang-Bitsuelas.jpg"),
            createCategoryButton("Chicken", "Pineapple Fried Chicken.jpg"),
            createCategoryButton("Kaldereta", "Ground Pork Kaldereta Omelet.jpg")
        );
        categoriesBox.setAlignment(Pos.CENTER);
        categoriesBox.setPadding(new Insets(20));

        // Layout
        VBox root = new VBox(15,
            backButton,
            title,
            subtitle,
            categoriesBox
        );
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(25));
        root.getStyleClass().add("categoriesRoot");

        // Scene Setup
        Scene scene = new Scene(root, 800, 450);
        scene.getStylesheets().add(getClass().getResource("/styles/categories.css").toExternalForm());

        primaryStage.setTitle("Categories");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Button createCategoryButton(String categoryName, String imagePath) {
        Image image = new Image(getClass().getResource("/com/images/" + imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Button button = new Button(categoryName, imageView);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
        button.getStyleClass().add("category-button");

        button.setOnAction(e -> {
            searchDish(categoryName);
            //close this window
            Stage currentStage = (Stage) button.getScene().getWindow();
            currentStage.close();
        });
        return button;
    }

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

    private void showResultsPage(List<Recipe> recipes) {
        Resultspage resultsPage = new Resultspage(recipes);
        Stage resultsStage = new Stage();
        resultsPage.start(resultsStage);
        
    }

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
