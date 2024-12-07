module com.example.inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires net.sf.jasperreports.core;


    opens com.example.inventorymanagementsystem to javafx.fxml;
    exports com.example.inventorymanagementsystem;
    exports com.example.inventorymanagementsystem.controller;
    opens com.example.inventorymanagementsystem.controller to javafx.fxml;
    exports com.example.inventorymanagementsystem.data;
    opens com.example.inventorymanagementsystem.data to javafx.fxml;
}