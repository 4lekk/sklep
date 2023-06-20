package com.sklepwjavie;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.*;

public class DbApp {

    public static void main(String[] args) {
        List<Product> products = new ArrayList<Product>();
        Scanner scanner = null;
        InputStreamReader ins;
        BufferedReader reader = null;

        SessionFactory sessionFactory = null;
        Session session = null;

        ProductService pService = null;

        try {
            scanner = new Scanner(System.in);
            ins = new InputStreamReader(System.in);
            reader = new BufferedReader(ins);

            Helper helper = new Helper(reader, scanner);
            pService = new ProductService();

            sessionFactory = pService.setUp();
            session = sessionFactory.openSession();
            pService.setSession(session);

            products = pService.getProductsFromDB();

            System.out.println("Witaj w sklepie. Oto co możesz zrobić:");
            helper.printMenu();

            while (true) {
                System.out.println();
                System.out.print("Wybierz opcję: ");
                int option = scanner.nextInt();
                switch (option) {
                    case 1: {
                        helper.printProducts(products);
                        break;
                    }
                    case 2: {
                        // add/save product do DB; klasa ProductService
                        Product product = helper.getProductToAdd(products);
                        pService.addToDB(product);
                        break;
                    }
                    case 3: {
                        Product p = helper.getProductToRemove(products);
                        if (p != null) {
                            pService.removeFromDB(p);
                        }
                        break;
                    }
                    case 4: {
                        if (helper.editProductLocal(products)) {
                            pService.editProductCharacteristicsInDB();
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                reader.close();
                session.close();
                sessionFactory.close();
            }
            catch (IOException e) {

            }
        }
    }
}
