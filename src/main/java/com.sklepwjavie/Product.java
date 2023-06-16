package com.sklepwjavie;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Product {

    private String name;

    private float price;

    private float weight;
    private LocalDateTime fullDate;

    private String date;

    private String description;

    public Product(String n, float p, float w, LocalDateTime ld, String d) {
        name = n;
        price = p;
        weight = w;
        fullDate = ld;
        date = ld.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        description = d;
    }

    public Product() {

    }

    @Override
    public String toString() {
        return name + ", " + Float.toString(price) + "," + Float.toString(weight) + ", " +
                date + ", " + description;
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
    public String getDate() {
        return date;
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
    public void setDate(String s) {
        date = s;
    }
}
