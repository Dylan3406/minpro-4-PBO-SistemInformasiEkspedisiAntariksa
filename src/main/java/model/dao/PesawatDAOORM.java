package model.dao;

import config.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import model.PesawatAntariksa;
import org.hibernate.SessionFactory;

public class PesawatDAOORM {
    private final SessionFactory sf = HibernateUtil.getSessionFactory();

    public boolean tambah(PesawatAntariksa p) {
        try {
            sf.inTransaction(session -> session.persist(p));
            return true;
        } catch (Exception ex) {
            System.out.println("Gagal tambah pesawat: " + ex.getMessage());
            return false;
        }
    }

    public List<PesawatAntariksa> getAll() {
        try {
            return sf.fromSession(session -> session.createSelectionQuery(
                "from PesawatAntariksa order by idPesawat", PesawatAntariksa.class).getResultList());
        } catch (Exception ex) {
            System.out.println("Gagal ambil pesawat: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    public PesawatAntariksa getById(int id) {
        try {
            return sf.fromSession(session -> session.find(PesawatAntariksa.class, id));
        } catch (Exception ex) {
            System.out.println("Gagal cari pesawat: " + ex.getMessage());
            return null;
        }
    }

    public List<PesawatAntariksa> cariByNama(String nama) {
        try {
            return sf.fromSession(session -> session.createSelectionQuery(
                "from PesawatAntariksa where lower(namaPesawat) like :nama", PesawatAntariksa.class)
                .setParameter("nama", "%" + nama.toLowerCase() + "%")
                .getResultList());
        } catch (Exception ex) {
            System.out.println("Gagal cari pesawat: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean ubah(PesawatAntariksa p) {
        try {
            sf.inTransaction(session -> session.merge(p));
            return true;
        } catch (Exception ex) {
            System.out.println("Gagal ubah pesawat: " + ex.getMessage());
            return false;
        }
    }

    public boolean hapus(int id) {
        try {
            return sf.fromTransaction(session -> {
                PesawatAntariksa p = session.find(PesawatAntariksa.class, id);
                if (p == null) return false;
                session.remove(p);
                return true;
            });
        } catch (Exception ex) {
            System.out.println("Gagal hapus pesawat: " + ex.getMessage());
            return false;
        }
    }

    public boolean idSudahAda(int id) {
        return getById(id) != null;
    }
}