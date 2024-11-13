package com.example.inventorymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class MainController {
    @FXML
    private Button close;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    //Database тохиргоо
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void loginAdmin() {
        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";

        connect = Database.connectDB();

        try {
            assert connect != null; // (DB холболт шалгана)
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());

            result = prepare.executeQuery();
            Alert alert;

            // аль нэг талбар хоосон бол
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Алдаа гарлаа");
                alert.setHeaderText(null);
                alert.setContentText("Хоосон талбаруудыг бөглөнө үү!");
                alert.showAndWait();
            }

            // амжилттай нэвтэрвэл
            if(result.next()) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Мэдэгдэл");
                alert.setHeaderText(null);
                alert.setContentText("Амжилттай нэвтэрлээ!");

                // Dashboard хуудас нээх
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Dashboard.fxml")));
                Stage stage = new Stage();
                stage.setTitle("Бараа агуулахын систем | Админ хуудас");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else { // Амжилтгүй болвол
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Алдаа гарлаа");
                alert.setHeaderText(null);
                alert.setContentText("Нэвтрэх нэр эсвэл нууц үг буруу байна!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // хаах улаан товч
    public void close() {
        System.exit(0);
    }
}