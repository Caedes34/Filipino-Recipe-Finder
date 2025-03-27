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
        // Title and subtitle
        Label title = new Label("Categories");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        title.setPadding(new Insets(0, 0, 0, 50));

        Label subtitle = new Label("Find delicious Filipino recipes by category");
        subtitle.setStyle("-fx-font-size: 14px;");
        subtitle.setPadding(new Insets(0, 0, 0, 100));

        // Categories
        HBox categories = new HBox(15);
        categories.setAlignment(Pos.CENTER);
        categories.setPadding(new Insets(20, 10, 20, 10));

        Button soupButton = createCategoryButton("Soups", "images/soup.jpg");
        Button noodleButton = createCategoryButton("Noodles", "images/noodles.jpg");
        Button meatButton = createCategoryButton("Meat", "images/meat.jpg");
        Button seafoodButton = createCategoryButton("Seafood", "images/seafood.jpg");
        Button dessertButton = createCategoryButton("Desserts", "images/dessert.jpg");

        categories.getChildren().addAll(soupButton, noodleButton, meatButton, seafoodButton, dessertButton);

        VBox root = new VBox(10, title, subtitle, categories);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 700, 400);
        primaryStage.setTitle("Categories");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Button createCategoryButton(String categoryName, String imagePath) {
      ImageView imageView = new ImageView(new Image("file:C:/Program Projects/RECIPE FINDER JAVA PROGJECT/src/resources/" + imagePath));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        
        Button button = new Button(categoryName, imageView);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        button.setOnAction(e -> System.out.println("Selected: " + categoryName));
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
