package com.example.inventorymanagementsystem.data;

import javafx.collections.ObservableList;
import javafx.scene.control.SpinnerValueFactory;

public class ListData {

    public static String username;

    public static String path;

    public static int customerId;

    public static String[] listType = {"Зууш", "Ундаа", "Десерт", "Хувийн бараа", "Бусад"};

    public static String[] listStatus = {"Боломжтой", "Боломжгүй"};

    public static ObservableList<ProductData> addProductsList;

    public static ObservableList<CustomerData> ordersList;

    public static double amountP;
    public static double totalP;
    public static double balanceP;
    public static int qty;

    public static SpinnerValueFactory<Integer> spinner;

    public static double x = 0;
    public static double y = 0;
}
