package com.sklepwjavie;

//import liquibase.Contexts;
//import liquibase.LabelExpression;
//import liquibase.Liquibase;
//import liquibase.Scope;
//import liquibase.database.Database;
//import liquibase.database.DatabaseFactory;
//import liquibase.database.jvm.JdbcConnection;
//import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class Config {
    protected SessionFactory setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        /*Map<String, Object> config = new HashMap<>();
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
        });*/
        SessionFactory sessionFactory = null;
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
        return sessionFactory;
    }

}
