package com.example.inventorymanagementsystem.data;

import java.sql.Date;

public class ProductData {
    private Integer productId;
    private String type;
    private String brand;
    private String productName;
    private Double price;
    private String status;
    private String image;
    private Date date;

    /**
     * ProductData объект нь барааны мэдээллийг илэрхийлнэ.
     *
     * @param productId   Барааны ID.
     * @param type        Бүтээгдэхүүний төрөл (жишээ нь, зууш, ундаа).
     * @param brand       Бүтээгдэхүүний брэнд.
     * @param productName Бүтээгдэхүүний нэр.
     * @param price       Бүтээгдэхүүн эсвэл захиалгын нийт үнэ.
     * @param status      Барааны төлөв.
     * @param image       Барааны зураг
     * @param date        Захиалгын огноо.
     **/

    public ProductData(Integer productId, String type, String brand, String productName, Double price, String status, String image, Date date) {
        this.productId = productId;
        this.type = type;
        this.brand = brand;
        this.productName = productName;
        this.price = price;
        this.status = status;
        this.image = image;
        this.date = date;
    }

    public Integer getProductId() {
        return productId;
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

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

}
