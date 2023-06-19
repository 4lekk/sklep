package com.sklepwjavie;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
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

    protected SessionFactory setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        SessionFactory sessionFactory = null;
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
        return sessionFactory;
    }

    public List<Product> getProductsFromDB(Session session) {
        session.beginTransaction();
        List<Product> products = session.createQuery("select p from Product p", Product.class)
                .list();
        session.getTransaction().commit();
        return products;
    }
    public void printProducts(List<Product> products) {
        if (products.isEmpty())
            System.out.println("Nie dodałeś jeszcze żadnych produktów");
        else {
            for (Product p : products) {
                System.out.println("Id: " + p.getProductId().longValue());
                System.out.println("Nazwa: " + p.getName());
                System.out.println("Cena: " + p.getPrice());
                System.out.println("Waga: " + p.getWeight());
//                System.out.println("Data dodania: " + p.getFullDate()
//                        .format(DateTimeFormatter.ISO_LOCAL_DATE));
                System.out.println("Data dodania: " + p.getFullDate().toString());
                System.out.println("Opis: " + p.getDescription());
                System.out.println();
            }
        }
    }
    public void addProduct(List<Product> products, Session session)
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
        p.setFullDate(tnow);
        System.out.println("Podaj opis nowego produktu: ");
        String desc = reader.readLine();
        p.setDescription(desc);
        session.beginTransaction();
        session.persist(p);
        session.getTransaction().commit();
        products.add(p);
    }

    public void deleteProducts(List<Product> products, Session session)
            throws IOException, SQLException {
        if (products.isEmpty()) {
            System.out.println("Nie dodałeś jescze żadnego produktu");
        } else {
            System.out.println("Podaj id produktu który chcesz usunąć: ");
            Long id = Long.parseLong(reader.readLine());
            if (products.stream().noneMatch(p -> p.getProductId().equals(id))) {
                System.out.println("Nie ma takiego produktu");
            } else {
                session.beginTransaction();
                List<Product> prod = session
                        .createQuery("select p from Product p where p.productId = "
                        + id.longValue(), Product.class).list();
                session.remove(prod.get(0));
                session.getTransaction().commit();
                products.removeIf(p -> p.getProductId().equals(id));
                System.out.println("Skutecznie usunąłeś produkt o id " + id.longValue());
            }
        }
    }

    public void editProduct(List<Product> products, Session session)
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
            session.beginTransaction();
            List<Product> prod = session.createQuery("select p from Product p where p.name='"
                    + name + "'", Product.class).list();
            switch (element) {
                case 1: {
                    System.out.print("Podaj nową cenę: ");
                    float price = Float.parseFloat(reader.readLine());
                    prod.get(0).setPrice(price);
                    products.stream().filter(p -> Objects.equals(p.getName(), name))
                            .toList().get(0).setPrice(price);
                    System.out.println("Skutecznie zmieniłeś cenę");
                }
                case 2: {
                    System.out.print("Podaj nową wagę: ");
                    float weight = Float.parseFloat(reader.readLine());
                    prod.get(0).setWeight(weight);
                    products.stream().filter(p -> Objects.equals(p.getName(), name))
                            .toList().get(0).setWeight(weight);
                    System.out.println("Skutecznie zmieniłeś wagę");
                }
                case 3: {
                    System.out.print("Podaj nowy opis: ");
                    String desc = reader.readLine();
                    prod.get(0).setDescription(desc);
                    products.stream().filter(p -> Objects.equals(p.getName(), name))
                            .toList().get(0).setDescription(desc);
                    System.out.println("Skutecznie zmieniłeś opis");
                }
            }

            session.getTransaction().commit();
        }
    }
}
