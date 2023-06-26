//package com.sklepwjavie;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Scanner;
//
//public class Helper {
//
//    BufferedReader reader;
//    Scanner scanner;
//
//    public void printMenu() {
//        System.out.println("1. Pokaż listę produktów");
//        System.out.println("2. Dodaj produkt");
//        System.out.println("3. Usuń produkt");
//        System.out.println("4. Zmień dane produktu");
//        System.out.println("5. Pokaż dostępne opcje");
//        System.out.println("6. Wyjdź");
//    }
//
//    Helper (BufferedReader b, Scanner s) {
//        reader = b;
//        scanner = s;
//    }
//
//    private Product.Characteristic chooseProductOrdering() throws IOException {
//        System.out.println("Sortuj produkty na podstawie:");
//        System.out.println("1 - nazwa, 2 - cena, 3 - waga, 4 - data dodania");
//        int s = Integer.parseInt(reader.readLine());
//        switch (s) {
//            case 1: {
//                return Product.Characteristic.NAME;
//            }
//            case 2: {
//                return Product.Characteristic.PRICE;
//            }
//            case 3: {
//                return Product.Characteristic.WEIGHT;
//            }
//            case 4: {
//                return Product.Characteristic.DATE;
//            }
//        }
//        return Product.Characteristic.NAME;
//    }
//
//    public void printProducts(List<Product> products) throws IOException {
//        if (products.isEmpty())
//            System.out.println("Nie dodałeś jeszcze żadnych produktów");
//        else {
//            List<Product> p_copy = new ArrayList<>(products);
//            Product.Characteristic c = chooseProductOrdering();
//            switch (c) {
//                case NAME: {
//                    p_copy.sort(Comparator.comparing(Product::getName));
//                    break;
//                }
//                case PRICE: {
//                    p_copy.sort(Comparator.comparing(Product::getPrice));
//                    break;
//                }
//                case WEIGHT: {
//                    p_copy.sort(Comparator.comparing(Product::getWeight));
//                    break;
//                }
//                case DATE: {
//                    p_copy.sort(Comparator.comparing(Product::getFullDate));
//                }
//            }
//            for (Product p : p_copy) {
//                System.out.println("Id: " + p.getProductId().longValue());
//                System.out.println("Nazwa: " + p.getName());
//                System.out.println("Cena: " + p.getPrice());
//                System.out.println("Waga: " + p.getWeight());
//                System.out.println("Data dodania: " + p.getFullDate()
//                        .format(DateTimeFormatter.ISO_LOCAL_DATE));
//                System.out.println("Opis: " + p.getDescription());
//                System.out.println();
//            }
//        }
//    }
//    public Product getProductToAdd(List<Product> products) throws IOException, SQLException {
//        Product p = new Product();
//        String name = "";
//        System.out.print("Podaj nazwę nowego produktu: ");
//        name = reader.readLine();
//        p.setName(name);
//        System.out.print("Podaj cenę nowego produktu: ");
//        float price = Float.parseFloat(reader.readLine());
//        p.setPrice(price);
//        System.out.println("Podaj wagę nowego produktu: ");
//        float weight = Float.parseFloat(reader.readLine());
//        p.setWeight(weight);
//        var tnow = LocalDateTime.now();
//        p.setFullDate(tnow);
//        System.out.println("Podaj opis nowego produktu: ");
//        String desc = reader.readLine();
//        p.setDescription(desc);
//        products.add(p);
//        return p;
//    }
//
//    public Product getProductToRemove(List<Product> products) throws IOException, SQLException {
//        Product product = null;
//        if (products.isEmpty()) {
//            System.out.println("Nie dodałeś jescze żadnego produktu");
//        } else {
//            System.out.println("Podaj id produktu który chcesz usunąć: ");
//            Long id = Long.parseLong(reader.readLine());
//            if (products.stream().noneMatch(p -> p.getProductId().equals(id))) {
//                System.out.println("Nie ma takiego produktu");
//            } else {
//                product = products.stream()
//                        .filter(p -> p.getProductId().equals(id)).toList().get(0);
//                products.remove(product);
//                System.out.println("Skutecznie usunąłeś produkt o id " + id.longValue());
//            }
//        }
//        return product;
//    }
//
//    public boolean editProductLocal(List<Product> products) throws IOException {
//        System.out.println("Podaj id produktu, który chcesz edytować: ");
//        Long id = Long.parseLong(reader.readLine());
//        List<Product> prods = products.stream()
//                .filter(p -> p.getProductId().equals(id)).toList();
//        Product p = null;
//        if (prods.isEmpty()) {
//            System.out.println("Nie ma takiego produktu");
//            return false;
//        }
//        else {
//            p = prods.get(0);
//            boolean loops;
//            do {
//                loops = false;
//                System.out.println("Który element chciałbyś zmienić?");
//                System.out.println("1 - cena, 2, - waga, 3 - opis");
//                System.out.print("Wybierz element: ");
//                int element = scanner.nextInt();
//                switch (element) {
//                    case 1: {
//                        System.out.print("Podaj nową cenę: ");
//                        float price = Float.parseFloat(reader.readLine());
//                        p.setPrice(price);
//                        System.out.println("Skutecznie zmieniłeś cenę");
//                        break;
//                    }
//                    case 2: {
//                        System.out.print("Podaj nową wagę: ");
//                        float weight = Float.parseFloat(reader.readLine());
//                        p.setWeight(weight);
//                        System.out.println("Skutecznie zmieniłeś wagę");
//                        break;
//                    }
//                    case 3: {
//                        System.out.print("Podaj nowy opis: ");
//                        String desc = reader.readLine();
//                        p.setDescription(desc);
//                        System.out.println("Skutecznie zmieniłeś opis");
//                        break;
//                    }
//                    default: {
//                        System.out.println("Podaj liczbę w przedziale od 1 do 3");
//                        loops = true;
//                    }
//                }
//            } while(loops == true);
//            return true;
//        }
//    }
//}
