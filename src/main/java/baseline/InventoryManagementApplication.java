/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 William Furie
 */

package baseline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class InventoryManagementApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));

        stage.setTitle("Inventory Manager");
        stage.setScene(scene);

        // min size
        stage.setMinHeight(450);
        stage.setMinWidth(640);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
