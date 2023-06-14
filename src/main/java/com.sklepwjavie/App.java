package com.sklepwjavie;

import java.io.*;
import java.util.*;

public class App {

    public static void main(String[] args) {
        List<Product> products = new ArrayList<Product>();
        Helper helper = new Helper();
        Scanner sc = null;
        InputStreamReader ins;
        BufferedReader reader = null;


        try {
            sc = new Scanner(System.in);
            ins = new InputStreamReader(System.in);
            reader = new BufferedReader(ins);

            System.out.println("Witaj w sklepie. Oto co możesz zrobić:");
            helper.printMenu();
//            String s = "";

            while (true) {
                System.out.println();
                System.out.print("Wybierz opcję: ");
                int option = sc.nextInt();
//                System.out.println();
                switch (option) {
                    case 1: {
                        if (products.isEmpty())
                            System.out.println("Nie dodałeś jeszcze żadnych produktów");
                        else {
                            for (Product p : products) {
                                System.out.println("Nazwa: " + p.getName());
                                System.out.println("Cena: " + p.getPrice());
                                System.out.println("Waga: " + p.getWeight());
                                System.out.println("Opis: " + p.getDescription());
                                System.out.println();
                            }
                        }
                        break;
                    }
                    case 2: {
                        Product p = new Product();
                        String name = "";
                        System.out.print("Podaj nazwę nowego produktu: ");
                        name = reader.readLine();
                        p.setName(name);
                        System.out.print("Podaj cenę nowego produktu: ");
                        p.setPrice(Float.parseFloat(reader.readLine()));
                        System.out.println("Podaj wagę nowego produktu: ");
                        p.setWeight(Float.parseFloat(reader.readLine()));
                        System.out.println("Podaj opis nowego produktu: ");
                        p.setDescription(reader.readLine());
                        products.add(p);
                        break;
                    }
                    case 3: {
                        if (products.isEmpty()) {
                            System.out.println("Nie dodałeś jescze żadnego produktu");
                        } else {
                            System.out.println("Podaj nazwę produktu który chcesz usunąć: ");
                            String name = reader.readLine();
                            //                        System.out.println(products.stream().filter(p -> p.getName() == name).count());
                            if (products.stream().noneMatch(p -> Objects.equals(p.getName(), name))) {
                                System.out.println("Nie ma takiego produktu");
                            } else {
                                //                            products.remove(name);
                                products.removeIf(p -> Objects.equals(p.getName(), name));
                                System.out.println("Skutecznie usunąłeś produkt " + name);
                            }
                        }
                        break;
                    }
                    case 4: {
                        System.out.println("Podaj nazwę produktu który chciałbyś edytować");
                        String name = reader.readLine();
                        if (products.stream().noneMatch(p -> Objects.equals(p.getName(), name)))
                            System.out.println("Nie ma takiego produktu");
                        else {
                            System.out.println("Który element chciałbyś zmienić?");
                            System.out.println("1 - cena, 2, - waga, 3 - opis");
                            System.out.print("Wybierz element: ");
                            int element = sc.nextInt();
                            if (element == 1) {
                                System.out.print("Podaj nową cenę: ");
                                products.stream().filter(p -> Objects.equals(p.getName(), name))
                                        .toList().get(0).setPrice(Float.parseFloat(reader.readLine()));
                                System.out.println("Skutecznie zmieniłeś cenę");
                            } else if (element == 2) {
                                System.out.print("Podaj nową wagę: ");
                                products.stream().filter(p -> Objects.equals(p.getName(), name))
                                        .toList().get(0).setWeight(Float.parseFloat(reader.readLine()));
                                System.out.println("Skutecznie zmieniłeś wagę");
                            } else if (element == 3) {
                                System.out.print("Podaj nowy opis: ");
                                products.stream().filter(p -> Objects.equals(p.getName(), name))
                                        .toList().get(0).setDescription(reader.readLine());
                                System.out.println("Skutecznie zmieniłeś opis");
                            }
                        }
                        break;
                    }
                    case 5:
                        helper.printMenu();
                        break;
                    case 6: {
                        System.out.println("Żegnamy i zapraszamy ponownie!");
                        System.exit(0);
                        break;
                    }
                    default:
                        System.out.println("Nie ma takiej opcji");
                        break;
                }
            }
        } catch (IOException e) {

        }
        catch (InputMismatchException e) {
            System.err.println("Proszę wpisać numer w przedziale 1-6");
        }


    }
}
