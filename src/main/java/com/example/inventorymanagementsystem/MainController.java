package com.example.inventorymanagementsystem;

import com.example.inventorymanagementsystem.data.ListData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class MainController {
    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane login_form;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_username;

    @FXML
    private PasswordField signup_password;

    @FXML
    private PasswordField signup_repassword;

    @FXML
    private TextField signup_username;

    //Database тохиргоо
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private double x = 0;
    private double y = 0;

    AlertMessage alert = new AlertMessage();

    private static final String DB_TABLE_USER = "user";

    // хэрэглэгч нэвтрэх функц
    public void loginUser() {
        String selectData = "SELECT * FROM " + DB_TABLE_USER + " WHERE username = ? and password = ?";
        connect = Database.connectDB();

        try {
            assert connect != null; // (DB холболт шалгана)
            prepare = connect.prepareStatement(selectData);
            prepare.setString(1, login_username.getText());
            prepare.setString(2, login_password.getText());
            result = prepare.executeQuery();

            // аль нэг талбар хоосон бол
            if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
                alert.errorMessage("Хоосон талбаруудыг бөглөнө үү!");
            }

            // амжилттай нэвтэрвэл
            if (result.next()) {
                ListData.username = login_username.getText();
                // login_username, login_password зөв бол dashboard form руу шилжинэ
                alert.successMessage("Амжилттай нэвтэрлээ!");
                // Логин цонхыг нуух
                loginBtn.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Dashboard.fxml")));
                Stage stage = new Stage();
                stage.setTitle("Бараа агуулахын систем | Админ хуудас");
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            } else { // Амжилтгүй болвол
                alert.errorMessage("Нэвтрэх нэр эсвэл нууц үг буруу байна!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //хэрэглэгч шинээр бүртгүүлэх функц
    public void signupUser() {
        String selectData = "SELECT * FROM " + DB_TABLE_USER + " WHERE username = '" + signup_username.getText() + "'";
        connect = Database.connectDB();

        try {
            assert connect != null;
            statement = connect.createStatement();
            result = statement.executeQuery(selectData);

            if (result.next()) {
                alert.errorMessage(signup_username.getText() + " нэртэй хэрэглэгч аль хэдийн бүртгэгдсэн байна.");
            }

            if (!signup_password.getText().equals(signup_repassword.getText())) {
                alert.errorMessage("Нууц үг таарахгүй байна.");
            }

            if (signup_password.getText().length() < 8) {
                alert.errorMessage("Нууц үг дор хаяж 8 тэмдэгтээс бүрдсэн байх ёстой.");
            }

            String insertData = "INSERT INTO " + DB_TABLE_USER + " (username, password) VALUES(?, ?)";
            prepare = connect.prepareStatement(insertData);
            prepare.setString(1, signup_username.getText());
            prepare.setString(2, signup_password.getText());
            prepare.executeUpdate();

            alert.successMessage("Амжилттай бүртгэгдлээ. Нэвтэрч орно уу.");
            loginForm();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void loginForm() {
        login_form.setVisible(true);
        signup_form.setVisible(false);
    }

    public void signupForm() {
        login_form.setVisible(false);
        signup_form.setVisible(true);
    }

    // хаах улаан товч
    public void close() { System.exit(0); }
}