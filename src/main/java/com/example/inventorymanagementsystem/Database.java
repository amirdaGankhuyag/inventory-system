package com.example.inventorymanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    public static Connection connectDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "" );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
