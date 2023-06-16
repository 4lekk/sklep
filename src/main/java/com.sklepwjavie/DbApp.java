package com.sklepwjavie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.format.DateTimeFormatter;
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

        String url = "jdbc:postgresql://localhost/mydb";
        String user = "postgres";
        String pwd = "laptopnastole";

        Connection connection = null;
        Statement statement = null;

        try {

            scanner = new Scanner(System.in);
            ins = new InputStreamReader(System.in);
            reader = new BufferedReader(ins);

            Helper helper = new Helper(reader, scanner);

            connection = DriverManager.getConnection(url, user, pwd);
            System.out.println("Connected to the database");
            statement = connection.createStatement();

            String createTable = "CREATE TABLE items (" +
                    "name          varchar(80)," +
                    "price         real," +
                    "weight         real," +
                    "added_on           timestamp," +
                    "description    varchar(120));";
            String checkIfTableExists =
                    "SELECT EXISTS (" +
                        "SELECT FROM pg_tables " +
                            "WHERE " +
                                "schemaname = 'public' AND " +
                                "tablename = 'items');";
            String queryItems =
                    "SELECT * FROM items;";
            ResultSet resultSet = statement.executeQuery(checkIfTableExists);
            while (resultSet.next()) {
                if (!resultSet.getBoolean("exists")) {
                    statement.execute(createTable);
                }
                else {
                    resultSet = statement.executeQuery(queryItems);
                    while (resultSet.next()) {
                        Product p = new Product();
                        p.setName(resultSet.getString("name"));
                        p.setPrice(resultSet.getFloat("price"));
                        p.setWeight(resultSet.getFloat("weight"));
                        p.setFullDate(resultSet.getTimestamp("added_on").toLocalDateTime());
                        p.setDate(p.getFullDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                        p.setDescription(resultSet.getString("description"));
                        products.add(p);
                    }
                }
            }
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
                        helper.addProduct(statement, products);
                        break;
                    }
                    case 3: {
                        helper.deleteProducts(statement, products);
                        break;
                    }
                    case 4: {
                        helper.editProduct(statement, products);
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
        } finally {
            try {
                reader.close();
                connection.close();
                statement.close();
            }
            catch (IOException e) {

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
