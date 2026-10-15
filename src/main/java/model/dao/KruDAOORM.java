package model.dao;

import config.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import model.Kru;
import org.hibernate.SessionFactory;

public class KruDAOORM {
    private final SessionFactory sf = HibernateUtil.getSessionFactory();

    public boolean tambah(Kru k) {
        try {
            sf.inTransaction(session -> session.persist(k));
            return true;
        } catch (Exception ex) {
            System.out.println("Gagal tambah kru: " + ex.getMessage());
            return false;
        }
    }

    public List<Kru> getAll() {
        try {
            return sf.fromSession(session -> session.createSelectionQuery(
                "from Kru order by idKru", Kru.class).getResultList());
        } catch (Exception ex) {
            System.out.println("Gagal ambil kru: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    public Kru getById(int id) {
        try {
            return sf.fromSession(session -> session.find(Kru.class, id));
        } catch (Exception ex) {
            System.out.println("Gagal cari kru: " + ex.getMessage());
            return null;
        }
    }

    public List<Kru> cariByNama(String nama) {
        try {
            return sf.fromSession(session -> session.createSelectionQuery(
                "from Kru where lower(nama) like :nama", Kru.class)
                .setParameter("nama", "%" + nama.toLowerCase() + "%")
                .getResultList());
        } catch (Exception ex) {
            System.out.println("Gagal cari kru: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean ubah(Kru k) {
        try {
            sf.inTransaction(session -> session.merge(k));
            return true;
        } catch (Exception ex) {
            System.out.println("Gagal ubah kru: " + ex.getMessage());
            return false;
        }
    }

    public boolean hapus(int id) {
        try {
            return sf.fromTransaction(session -> {
                Kru k = session.find(Kru.class, id);
                if (k == null) return false;
                session.remove(k);
                return true;
            });
        } catch (Exception ex) {
            System.out.println("Gagal hapus kru: " + ex.getMessage());
            return false;
        }
    }

    public boolean idSudahAda(int id) {
        return getById(id) != null;
    }
}