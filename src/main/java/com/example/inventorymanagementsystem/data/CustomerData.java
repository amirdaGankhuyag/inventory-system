package com.example.inventorymanagementsystem.data;

import java.sql.Date;

/**
* CustomerData класс нь захиалагчийн мэдээллийг хадгална.
* */

public class CustomerData {
    private Integer customerId;
    private String type;
    private String brand;
    private String productName;
    private Integer quantity;
    private Double price;
    private Date date;

    /**
     * CustomerData объектын бүх талбарыг эхлүүлэх байгуулагч.
     *
     * @param customerId  Хэрэглэгчийн ID.
     * @param type        Бүтээгдэхүүний төрөл (жишээ нь, зууш, ундаа).
     * @param brand       Бүтээгдэхүүний брэнд.
     * @param productName Бүтээгдэхүүний нэр.
     * @param quantity    Захиалсан бүтээгдэхүүний тоо хэмжээ.
     * @param price       Бүтээгдэхүүн эсвэл захиалгын нийт үнэ.
     * @param date        Захиалгын огноо.
     **/

    public CustomerData(Integer customerId, String type, String brand, String productName, Integer quantity, Double price, Date date) {
        this.customerId = customerId;
        this.type = type;
        this.brand = brand;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    /**
     * Getters
     **/
    public Integer getCustomerId() {
        return customerId;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
}
