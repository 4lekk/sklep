package com.sklepwjavie;
import com.opencsv.bean.CsvBindByPosition;

public class Product {
    @CsvBindByPosition(position = 0)
    private String name;
    @CsvBindByPosition(position = 1)
    private float price;
    @CsvBindByPosition(position = 2)
    private float weight;
    @CsvBindByPosition(position = 3)
    private String description;

    public Product(String n, float p, float w, String d) {
        name = n;
        price = p;
        weight = w;
        description = d;
    }

    public Product() {

    }

    @Override
    public String toString() {
        return name + ", " + Float.toString(price) + "," + Float.toString(weight) + ", " + description;
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
}
