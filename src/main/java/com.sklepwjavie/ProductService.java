package com.sklepwjavie;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {

    private Session session;

    protected SessionFactory setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        Map<String, Object> config = new HashMap<>();
        Scope.child(config, () -> {
            MetadataSources metadataSources = new MetadataSources(registry);
            Connection connection = metadataSources
                    .getServiceRegistry().getService(ConnectionProvider.class).getConnection();
            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Database database = DatabaseFactory
                    .getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            Liquibase liquibase = new Liquibase("dbchangelog.xml",
                    new ClassLoaderResourceAccessor(), database);

            liquibase.update(new Contexts(), new LabelExpression());
        });
        SessionFactory sessionFactory = null;
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
        return sessionFactory;
    }

    public List<Product> getProductsFromDB() {
        session.beginTransaction();
        List<Product> products = session.createQuery("select p from Product p", Product.class)
                .list();
        session.getTransaction().commit();
        return products;
    }

    public void addToDB(Product p) {
        session.beginTransaction();
        session.persist(p);
        session.getTransaction().commit();
    }
    public void removeFromDB(Product p) {
        session.beginTransaction();
        Product prod = session
                .createQuery("select p from Product p where p.productId = "
                        + p.getProductId(), Product.class).list().get(0);
        session.remove(prod);
        session.getTransaction().commit();
    }
    public void editProductCharacteristicsInDB() {
        session.beginTransaction();

        session.getTransaction().commit();
    }

    public Session getSession() {
        return session;
    }
    public void setSession(Session s) {
        session = s;
    }
}
