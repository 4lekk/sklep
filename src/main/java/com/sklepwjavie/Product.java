package com.sklepwjavie;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

@Entity
@Table(name = "items")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long productId;

    @NotBlank
    private String name;

    @PositiveOrZero
    private float price;

    @PositiveOrZero
    private float weight;

    @Column(name = "added_on")
    private LocalDateTime fullDate;

    private String description;

    @Transient
    private String formattedDate;

    public Product(String n, float p, float w, String d) {
        name = n;
        price = p;
        weight = w;
        description = d;
    }

    public Product() {

    }

    public Long getProductId() {
        return productId;
    }
    public String getName()
    {
        return name;
    }
    public float getPrice()
    {
        return price;
    }
    public float getWeight()
    {
        return weight;
    }
    public String getDescription()
    {
        return description;
    }
    public LocalDateTime getFullDate() {
        return fullDate;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setProductId(Long p) {
        productId = p;
    }
    public void setName(String s)
    {
        name = s;
    }
    public void setPrice(float s)
    {
        price = s;
    }
    public void setWeight(float s)
    {
        weight = s;
    }
    public void setDescription(String s)
    {
        description = s;
    }
    public void setFullDate(LocalDateTime d){
        fullDate = d;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }
}
