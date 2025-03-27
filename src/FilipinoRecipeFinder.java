import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class FilipinoRecipeFinder extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Title and subtitle
        Label title = new Label("Filipino Recipe Finder");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;"); 
        title.setPadding(new Insets(0, 0, 0, 150));
        Label subtitle = new Label("Find delicious Filipino recipes by dish type or ingredients");
        subtitle.setStyle("-fx-font-size: 14px;");
        subtitle.setPadding(new Insets(0, 0, 0, 100));

        // Toggle buttons for search type
        ToggleGroup toggleGroup = new ToggleGroup();
        ToggleButton searchByDish = new ToggleButton("Search by Dish Type");
        ToggleButton searchByIngredients = new ToggleButton("Search by Ingredients");
        ToggleButton searchByCategory = new ToggleButton("Search by Categories");
        searchByDish.setToggleGroup(toggleGroup);
        searchByIngredients.setToggleGroup(toggleGroup);
        searchByDish.setSelected(true);

        HBox toggleBox = new HBox(10, searchByDish, searchByIngredients, searchByCategory);
        toggleBox.setPadding(new Insets(10, 10, 10, 80));
        toggleBox.setPrefSize(30, 30);


        Label label1 = new Label("Enter Dish name(eg., Adobo, Sinigang, Pancit): ");
        label1.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");



        TextField searchField = new TextField();
        searchField.setPromptText("Enter dish name...");
        searchField.setPrefWidth(450);
        searchField.setMaxWidth(Double.MAX_VALUE);



        Button searchButton = new Button("🔍 Search");

        searchButton.setPrefWidth(100);
        searchButton.setMaxWidth(Double.MAX_VALUE);
        

        HBox searchBox = new HBox(10,label1 ,searchField, searchButton);
        //top,right,bottom,left
        searchBox.setPadding(new Insets(10, 0, 10, 10));
        searchBox.setAlignment(Pos.CENTER_RIGHT);

        
        // Popular searches
        Label popularLabel = new Label("Popular Searches:");
        HBox popularSearches = new HBox(10,
            new Button("Adobo"),
            new Button("Sinigang"),
            new Button("Pancit"),
            new Button("Lumpia"),
            new Button("Kare-kare"),
            new Button("Lechon")
        );

        VBox root = new VBox(10, title, subtitle, toggleBox, label1,searchBox, popularLabel, popularSearches);
        root.setPadding(new Insets(20));
        Scene scene = new Scene(root, 600, 320);




        primaryStage.setTitle("Filipino Recipe Finder");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        

        searchButton.setOnAction(e -> {
            RecipeDetails recipeDetailsWindow = new RecipeDetails();
            Stage newStage = new Stage();
            recipeDetailsWindow.start(newStage);

            Stage currentStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            currentStage.close();
        });

        searchByCategory.setOnAction(e -> {
            try {
                Categories categoriesPage = new Categories();
                Stage categoryStage = new Stage();
                categoriesPage.start(categoryStage); // Open Categories window
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    

    

    public static void main(String[] args) {
        launch(args);
    }
}
