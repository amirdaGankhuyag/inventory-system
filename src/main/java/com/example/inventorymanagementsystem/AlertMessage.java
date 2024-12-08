package com.example.inventorymanagementsystem;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Төрөл бүрийн аlert-уудыг агуулсан класс.
 * */
public class AlertMessage {

    private Alert alert;

    public void successMessage(String message) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Мэдэгдэл");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void errorMessage(String message) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Алдааны мэдэгдэл");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public boolean confirmMessage(String message) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Баталгаажуулалт");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> option = alert.showAndWait();

        return option.get().equals(ButtonType.OK);
    }
}