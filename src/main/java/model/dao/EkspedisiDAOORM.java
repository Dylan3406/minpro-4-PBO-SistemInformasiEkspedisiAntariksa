package model.dao;

import config.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import model.Ekspedisi;
import org.hibernate.SessionFactory;

public class EkspedisiDAOORM {
    private final SessionFactory sf = HibernateUtil.getSessionFactory();

    public boolean tambah(Ekspedisi e) {
        try {
            sf.inTransaction(session -> session.persist(e));
            return true;
        } catch (Exception ex) {
            System.out.println("Gagal tambah ekspedisi: " + ex.getMessage());
            return false;
        }
    }

    public List<Ekspedisi> getAll() {
        try {
            return sf.fromSession(session -> session.createSelectionQuery(
                "from Ekspedisi order by idEkspedisi", Ekspedisi.class).getResultList());
        } catch (Exception ex) {
            System.out.println("Gagal ambil ekspedisi: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    public Ekspedisi getById(int id) {
        try {
            return sf.fromSession(session -> session.find(Ekspedisi.class, id));
        } catch (Exception ex) {
            System.out.println("Gagal cari ekspedisi: " + ex.getMessage());
            return null;
        }
    }

    public List<Ekspedisi> cariByNama(String nama) {
        try {
            return sf.fromSession(session -> session.createSelectionQuery(
                "from Ekspedisi where lower(namaEkspedisi) like :nama", Ekspedisi.class)
                .setParameter("nama", "%" + nama.toLowerCase() + "%")
                .getResultList());
        } catch (Exception ex) {
            System.out.println("Gagal cari ekspedisi: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean ubah(Ekspedisi e) {
        try {
            sf.inTransaction(session -> session.merge(e));
            return true;
        } catch (Exception ex) {
            System.out.println("Gagal ubah ekspedisi: " + ex.getMessage());
            return false;
        }
    }

    public boolean hapus(int id) {
        try {
            return sf.fromTransaction(session -> {
                Ekspedisi e = session.find(Ekspedisi.class, id);
                if (e == null) return false;
                session.remove(e);
                return true;
            });
        } catch (Exception ex) {
            System.out.println("Gagal hapus ekspedisi: " + ex.getMessage());
            return false;
        }
    }

    public boolean idSudahAda(int id) {
        return getById(id) != null;
    }
}