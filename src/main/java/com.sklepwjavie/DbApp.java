package com.sklepwjavie;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class DbApp {

    public static void main(String[] args) {
        List<Product> products = new ArrayList<Product>();
        Scanner scanner = null;
        InputStreamReader ins;
        BufferedReader reader = null;

        SessionFactory sessionFactory = null;
        Session session = null;

        try {
            scanner = new Scanner(System.in);
            ins = new InputStreamReader(System.in);
            reader = new BufferedReader(ins);

            Helper helper = new Helper(reader, scanner);

            sessionFactory = helper.setUp();
            session = sessionFactory.openSession();
            products = helper.getProductsFromDB(session);

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
                        helper.addProduct(products, session);   // ma zwracać produkt
                        // add/save product do DB; klasa ProductService
                        break;
                    }
                    case 3: {
                        helper.deleteProducts(products, session);
                        break;
                    }
                    case 4: {
                        helper.editProduct(products, session);
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
