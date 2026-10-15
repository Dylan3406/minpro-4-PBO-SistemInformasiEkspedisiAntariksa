package config;

import model.Astronot;
import model.Ekspedisi;
import model.Kru;
import model.PesawatAntariksa;
import model.Teknisi;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                .setProperty("jakarta.persistence.jdbc.url",
                        "jdbc:mysql://localhost:3306/ekspedisi_antariksa")
                .setProperty("jakarta.persistence.jdbc.user", "root")
                .setProperty("jakarta.persistence.jdbc.password", "")
                .setProperty("hibernate.show_sql", "true")
                .addAnnotatedClass(Ekspedisi.class)
                .addAnnotatedClass(Kru.class)
                .addAnnotatedClass(Astronot.class)
                .addAnnotatedClass(Teknisi.class)
                .addAnnotatedClass(PesawatAntariksa.class)
                .buildSessionFactory();
        } catch (HibernateException ex) {
            System.out.println("Gagal membuat SessionFactory: " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}