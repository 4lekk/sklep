package com.sklepwjavie;

import org.hibernate.engine.jdbc.spi.SchemaNameResolver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Helper {

    BufferedReader reader;
    Scanner scanner;
    public void printMenu() {
        System.out.println("1. Pokaż listę produktów");
        System.out.println("2. Dodaj produkt");
        System.out.println("3. Usuń produkt");
        System.out.println("4. Zmień dane produktu");
        System.out.println("5. Pokaż dostępne opcje");
        System.out.println("6. Wyjdź");
    }

    Helper (BufferedReader b, Scanner s) {
        reader = b;
        scanner = s;
    }
    public void printProducts(List<Product> products) {
        if (products.isEmpty())
            System.out.println("Nie dodałeś jeszcze żadnych produktów");
        else {
            for (Product p : products) {
                System.out.println("Nazwa: " + p.getName());
                System.out.println("Cena: " + p.getPrice());
                System.out.println("Waga: " + p.getWeight());
                System.out.println("Data dodania: " + p.getDate());
                System.out.println("Opis: " + p.getDescription());
                System.out.println();
            }
        }
    }
    public void addProduct(Statement statement, List<Product> products)
            throws IOException, SQLException {
        Product p = new Product();
        String name = "";
        System.out.print("Podaj nazwę nowego produktu: ");
        name = reader.readLine();
        p.setName(name);
        System.out.print("Podaj cenę nowego produktu: ");
        float price = Float.parseFloat(reader.readLine());
        p.setPrice(price);
        System.out.println("Podaj wagę nowego produktu: ");
        float weight = Float.parseFloat(reader.readLine());
        p.setWeight(weight);
        var tnow = LocalDateTime.now();
        String SQLDate = tnow.getYear() + "-" +
                tnow.getMonthValue() + "-" +
                tnow.getDayOfMonth() + " " +
                tnow.getHour() + ":" +
                tnow.getMinute() + ":" +
                tnow.getSecond();
        p.setFullDate(tnow);
        p.setDate(p.getFullDate().
                format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println("Podaj opis nowego produktu: ");
        String desc = reader.readLine();
        p.setDescription(desc);
        String SQLAddItem =
                "INSERT INTO items (name, price, weight, added_on, description)" +
                        "VALUES ('" + name + "', " + price + ", " + weight + ", '" +
                        SQLDate + "', '" + desc + "');";
        statement.executeUpdate(SQLAddItem);
        products.add(p);
    }

    public void deleteProducts(Statement statement, List<Product> products)
            throws IOException, SQLException {
        if (products.isEmpty()) {
            System.out.println("Nie dodałeś jescze żadnego produktu");
        } else {
            System.out.println("Podaj nazwę produktu który chcesz usunąć: ");
            String name = reader.readLine();
            if (products.stream().noneMatch(p -> Objects.equals(p.getName(), name))) {
                System.out.println("Nie ma takiego produktu");
            } else {
                String SQLRemove =
                        "DELETE FROM items WHERE name = '" + name + "';";
                statement.executeUpdate(SQLRemove);
                products.removeIf(p -> Objects.equals(p.getName(), name));
                System.out.println("Skutecznie usunąłeś produkt " + name);
            }
        }
    }

    public void editProduct(Statement statement, List<Product> products)
            throws IOException, SQLException {
        if (products.isEmpty()) {
            System.out.println("Nie dodałeś jescze żadnego produktu");
        }
        System.out.println("Podaj nazwę produktu który chciałbyś edytować");
        String name = reader.readLine();
        if (products.stream().noneMatch(p -> Objects.equals(p.getName(), name)))
            System.out.println("Nie ma takiego produktu");
        else {
            System.out.println("Który element chciałbyś zmienić?");
            System.out.println("1 - cena, 2, - waga, 3 - opis");
            System.out.print("Wybierz element: ");
            int element = scanner.nextInt();
            if (element == 1) {
                System.out.print("Podaj nową cenę: ");
                float price = Float.parseFloat(reader.readLine());
                String SQLUpdatePrice =
                        "UPDATE items SET price = " + price + " WHERE name = '" +
                                name + "';";
                statement.executeUpdate(SQLUpdatePrice);
                products.stream().filter(p -> Objects.equals(p.getName(), name))
                        .toList().get(0).setPrice(price);
                System.out.println("Skutecznie zmieniłeś cenę");
            } else if (element == 2) {
                System.out.print("Podaj nową wagę: ");
                float weight = Float.parseFloat(reader.readLine());
                String SQLUpdateWeight =
                        "UPDATE items SET weight = " + weight + " WHERE name = '" +
                                name + "';";
                statement.executeUpdate(SQLUpdateWeight);
                products.stream().filter(p -> Objects.equals(p.getName(), name))
                        .toList().get(0).setWeight(weight);
                System.out.println("Skutecznie zmieniłeś wagę");
            } else if (element == 3) {
                System.out.print("Podaj nowy opis: ");
                String desc = reader.readLine();
                String SQLUpdateDesc =
                        "UPDATE items SET description = '" + desc + "' WHERE name = '" +
                                name + "';";
                statement.executeUpdate(SQLUpdateDesc);
                products.stream().filter(p -> Objects.equals(p.getName(), name))
                        .toList().get(0).setDescription(desc);
                System.out.println("Skutecznie zmieniłeś opis");
            }
        }
    }
}
