package com.sklepwjavie;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class FileApp {

    /*public static void readProducts(BufferedReader reader, List<Product> products)
            throws IOException {
        String line = null;
        while ((line = reader.readLine()) != null) {
            String name = line.substring(line.indexOf(" ") + 1);
            line = reader.readLine();
            float price = Float.parseFloat(line.substring(line.indexOf(" ") + 1));
            line = reader.readLine();
            float weight = Float.parseFloat(line.substring(line.indexOf(" ") + 1));
            line = reader.readLine();
            String description = line.substring(line.indexOf(" ") + 1);
            products.add(new Product(name, price, weight, description));
        }
    }*/

    /*public static void writeProducts(StatefulBeanToCsv<Product> beanToCsv, List<Product> products)
            throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        beanToCsv.write(products);
    }*/

    public static void main(String[] args) {
        List<Product> products = new ArrayList<Product>();
        Helper helper = new Helper();
        Scanner sc = null;
        InputStreamReader ins;
        BufferedReader reader = null;
        BufferedReader file_reader = null;
        BufferedWriter file_writer = null;

        File file;

        Path path =
                Paths.get(System.getProperty("user.dir")).resolve("src\\main\\resources\\items.txt");

        try {
            file = new File(path.toString());

            file.createNewFile();
            System.out.println("made it here");

            file_reader = Files.newBufferedReader(path);

            sc = new Scanner(System.in);
            ins = new InputStreamReader(System.in);
            reader = new BufferedReader(ins);
//            readProducts(file_reader, products);


            ColumnPositionMappingStrategy<Product> strat = new ColumnPositionMappingStrategy<>();
            strat.setType(Product.class);
            String[] fields = {"name", "price", "weight", "description"};
            strat.setColumnMapping(fields);
            CsvToBean<Product> csvToBean = new CsvToBeanBuilder<Product>(file_reader)
                    .withMappingStrategy(strat)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            products = csvToBean.parse();

            file_writer = Files.newBufferedWriter(path);
            StatefulBeanToCsv<Product> beanToCsv = new StatefulBeanToCsvBuilder<Product>(file_writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            System.out.println("Witaj w sklepie. Oto co możesz zrobić:");
            helper.printMenu();

            while (true) {
                System.out.println();
                System.out.print("Wybierz opcję: ");
                int option = sc.nextInt();
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
                            if (products.stream().noneMatch(p -> Objects.equals(p.getName(), name))) {
                                System.out.println("Nie ma takiego produktu");
                            } else {
                                products.removeIf(p -> Objects.equals(p.getName(), name));
                                System.out.println("Skutecznie usunąłeś produkt " + name);
                            }
                        }
                        break;
                    }
                    case 4: {
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
//                        writeProducts(beanToCsv, products);
                        beanToCsv.write(products);
                        file_writer.flush();
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
        } catch (CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        } catch (CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                reader.close();
                file_reader.close();
                file_writer.close();
            }
            catch (IOException e) {

            }
        }
    }
}
