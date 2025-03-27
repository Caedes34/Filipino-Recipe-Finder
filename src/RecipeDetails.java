import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RecipeDetails extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Back and Search buttons
        Button backButton = new Button("← Back to Results");
        Button searchButton = new Button("New Search");
        HBox topBar = new HBox(backButton, new Region(), searchButton);
        HBox.setHgrow(topBar.getChildren().get(1), Priority.ALWAYS);
        
        // Recipe Image and Title
        ImageView recipeImage = new ImageView(new Image("file:src/resources/images/chicken_adobo.jpg"));
        recipeImage.setFitHeight(200);
        recipeImage.setFitWidth(200);
        recipeImage.setPreserveRatio(true);
        Label title = new Label("Chicken Adobo");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Label description = new Label("Classic Filipino dish with chicken marinated in vinegar, soy sauce, and spices.");
        
        VBox titleContainer = new VBox(title, description);
        titleContainer.setSpacing(5);
        titleContainer.setAlignment(Pos.CENTER_LEFT);

        HBox titleBox = new HBox(recipeImage, titleContainer);
        titleBox.setSpacing(5);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        // Metadata
        Label timeLabel = new Label("⏳ 45 mins");
        Label servingsLabel = new Label("🍽️ 4 servings");
        Label cuisineLabel = new Label("🌍 Filipino Cuisine");
        HBox metadata = new HBox(10, timeLabel, servingsLabel, cuisineLabel);

        // Tabs for Ingredients and Instructions
        TabPane tabPane = new TabPane();
        Tab ingredientsTab = new Tab("Ingredients", createIngredientsGrid());
        Tab instructionsTab = new Tab("Instructions", createInstructionsGrid());
        tabPane.getTabs().addAll(ingredientsTab, instructionsTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        // Layout setup
        VBox root = new VBox(10, topBar, titleBox, metadata, tabPane);
        root.setPadding(new Insets(15));
        
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("Recipe Details");
        primaryStage.show();

        searchButton.setOnAction(e -> {
            FilipinoRecipeFinder FilipinoRecipeFinder = new FilipinoRecipeFinder();
            Stage newStage = new Stage();
            FilipinoRecipeFinder.start(newStage);


            Stage currentStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            currentStage.close();
        });

        backButton.setOnAction(e -> {
            FilipinoRecipeFinder FilipinoRecipeFinder = new FilipinoRecipeFinder();
            Stage newStage = new Stage();
            FilipinoRecipeFinder.start(newStage);

            Stage currentStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            currentStage.close();
        });
    }

    
    private GridPane createInstructionsGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
    
        String[][] instructions = {
            {"1: ", "Marinate the Chicken – In a bowl, combine chicken thighs, soy sauce, white vinegar, crushed garlic, bay leaves, and black peppercorns. Let it marinate for at least 30 minutes (or overnight for better flavor)."},
            {"2: ", "Sauté the Aromatics – Heat cooking oil in a pan over medium heat. Sauté the garlic until fragrant."},
            {"3: ", "Brown the Chicken – Add the marinated chicken (reserve the marinade) and sear until lightly browned on both sides."},
            {"4: ", "Simmer with Marinade – Pour in the marinade along with water and brown sugar. Let it come to a boil, then lower the heat and simmer for about 30–40 minutes or until the chicken is tender. Stir occasionally."},
            {"5: ", "Reduce the Sauce – If you want a thicker sauce, let it simmer uncovered for the last 5–10 minutes until it reduces."},
            {"6: ", "Serve – Remove from heat, discard the bay leaves, and serve hot with steamed rice."},
        };
    
        for (int i = 0; i < instructions.length; i++) {
            Label stepLabel = new Label("• " + instructions[i][0]); // Step Number
            stepLabel.setStyle("-fx-font-weight: bold;");
    
            Label descriptionLabel = new Label(instructions[i][1]); // Step Description
            descriptionLabel.setWrapText(true);  // Allows text wrapping for longer instructions
            descriptionLabel.setMaxWidth(400);   // Adjust width to prevent overflow
    
            grid.add(stepLabel, 0, i);       // Column 0: Step Number
            grid.add(descriptionLabel, 1, i); // Column 1: Step Description
        }
    
        return grid;
    }
    

    private GridPane createIngredientsGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        String[][] ingredients = {
            {"Chicken thighs", "2 lbs"},
            {"White vinegar", "1/4 cup"},
            {"Bay leaves", "3 pieces"},
            {"Brown sugar", "1 tbsp"},
            {"Water", "1 cup"},
            {"Soy sauce", "1/4 cup"},
            {"Garlic", "6 cloves, crushed"},
            {"Black peppercorns", "1 tsp"},
            {"Cooking oil", "2 tbsp"}
        };
       
            for (int i = 0; i < ingredients.length; i++) {
                Label nameLabel = new Label("• " + ingredients[i][0]);
                nameLabel.setStyle("-fx-font-weight: bold;");
                Label amountLabel = new Label(ingredients[i][1]);
                grid.addRow(i / 2, nameLabel, amountLabel);
            }
    
            return grid;
        };
    


}