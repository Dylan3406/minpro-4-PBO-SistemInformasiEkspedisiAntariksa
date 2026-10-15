package controller;

import model.dao.KruDAOORM;
import java.util.ArrayList;
import java.util.List;
import model.Astronot;
import model.EntitasAntariksa;
import model.Kru;
import model.Teknisi;
import view.View;

public class KruController {
    private final KruDAOORM kruDAO = new KruDAOORM();
    private final View view;

    public KruController(View view) {
        this.view = view;
    }

    public void tambahData() {
        view.cetak("\n--- TAMBAH DATA KRU ---");
        int id = view.inputIntPositif("ID Kru: ");
        if (kruDAO.idSudahAda(id)) {
            view.cetak("ID Kru sudah digunakan!");
            return;
        }
        String nama = view.inputString("Nama: ");
        int usia = view.inputIntPositif("Usia: ");
        view.cetak("\nJenis Kru:");
        view.cetak("1. Astronot");
        view.cetak("2. Teknisi");
        int jenis = view.inputInt("Pilih jenis kru: ");

        Kru kruBaru;
        switch (jenis) {
            case 1 -> {
                String spesialisasi = view.inputString("Spesialisasi: ");
                int jamTerbang = view.inputIntNonNegatif("Jam Terbang: ");
                kruBaru = new Astronot(id, nama, usia, spesialisasi, jamTerbang);
            }
            case 2 -> {
                String bidang = view.inputString("Bidang Keahlian: ");
                String sertifikasi = view.inputString("Sertifikasi: ");
                kruBaru = new Teknisi(id, nama, usia, bidang, sertifikasi);
            }
            default -> {
                view.cetak("\nJenis kru tidak dikenali, data dibatalkan.");
                return;
            }
        }

        if (kruDAO.tambah(kruBaru)) {
            view.cetak("\nData kru berhasil ditambahkan!");
        } else {
            view.cetak("\nData kru gagal ditambahkan.");
        }
    }

    public void tampilkanData() {
        view.cetak("\n--- DATA KRU ---");
        List<Kru> daftar = kruDAO.getAll();
        if (daftar.isEmpty()) {
            view.cetak("Belum ada data kru.");
            return;
        }
        for (Kru k : daftar) {
            view.tampilkanGaris();
            k.tampilkanData();
        }
        view.tampilkanGaris();
    }

    public void ubahData() {
        view.cetak("\n--- UBAH DATA KRU ---");
        if (kruDAO.getAll().isEmpty()) {
            view.cetak("Belum ada data kru.");
            return;
        }
        int id = view.inputIntPositif("Masukkan ID kru yang ingin diubah: ");
        Kru k = cariKru(id);

        if (k == null) {
            view.cetak("\nID kru tidak ditemukan.");
            return;
        }
        k.setNama(view.inputString("Nama baru: "));
        k.setUsia(view.inputIntPositif("Usia baru: "));
        switch (k) {
            case Astronot a -> {
                a.setSpesialisasi(view.inputString("Spesialisasi baru: "));
                a.setJamTerbang(view.inputIntNonNegatif("Jam Terbang baru: "));
            }
            case Teknisi t -> {
                t.setBidangKeahlian(view.inputString("Bidang Keahlian baru: "));
                t.setSertifikasi(view.inputString("Sertifikasi baru: "));
            }
            default -> {
            }
        }
        if (kruDAO.ubah(k)) {
            view.cetak("\nData berhasil diubah!");
        } else {
            view.cetak("\nData gagal diubah.");
        }
    }

    public void hapusData() {
        view.cetak("\n--- HAPUS DATA KRU ---");
        if (kruDAO.getAll().isEmpty()) {
            view.cetak("Belum ada data kru.");
            return;
        }
        int id = view.inputIntPositif("Masukkan ID kru yang ingin dihapus: ");
        if (cariKru(id) == null) {
            view.cetak("\nID kru tidak ditemukan.");
            return;
        }
        if (kruDAO.hapus(id)) {
            view.cetak("\nData berhasil dihapus!");
        } else {
            view.cetak("\nData gagal dihapus.");
        }
    }

    public List<EntitasAntariksa> getSemuaEntitas() {
        return new ArrayList<>(kruDAO.getAll());
    }

    public Kru cariKru(int id) {
        return kruDAO.getById(id);
    }

    public Kru cariKru(String nama) {
        for (Kru k : kruDAO.cariByNama(nama)) {
            if (k.getNama().equalsIgnoreCase(nama)) {
                return k;
            }
        }
        return null;
    }
}