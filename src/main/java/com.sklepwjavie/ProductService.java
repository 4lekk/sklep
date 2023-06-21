package com.sklepwjavie;

import org.hibernate.Session;

import java.util.List;

public class ProductService {

    private final Session session;

    ProductService(Session s) {
        session = s;
    }
    public List<Product> getProducts() {
        session.beginTransaction();
        List<Product> products = session.createQuery("select p from Product p", Product.class)
                .list();
        session.getTransaction().commit();
        return products;
    }

    public void save(Product p) {
        session.beginTransaction();
        session.persist(p);
        session.getTransaction().commit();
    }
    public void remove(Product p) {
        session.beginTransaction();
        Product prod = session
                .createQuery("select p from Product p where p.productId = "
                        + p.getProductId(), Product.class).list().get(0);
        session.remove(prod);
        session.getTransaction().commit();
    }
    public void editProductCharacteristics() {
        session.beginTransaction();

        session.getTransaction().commit();
    }
}
