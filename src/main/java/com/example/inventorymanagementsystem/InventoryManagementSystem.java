package com.example.inventorymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class InventoryManagementSystem extends Application {
    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage stage) throws IOException {
//        FXML file iig load hiih, root node oo avah
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementSystem.class.getResource("inventorysystem.fxml"));
        Parent root = fxmlLoader.load();

//        Scene ee uusgeh
        Scene scene = new Scene(root);

//        root node deer mouse press event handler nemeh
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
//        window oo drag hiihiin tuld mouse drag event nemeh
        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);

            stage.setOpacity(.8);
        });
//        mouse release
        root.setOnMouseReleased((MouseEvent event) -> {
            stage.setOpacity(1);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Бараа агуулахын систем");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}