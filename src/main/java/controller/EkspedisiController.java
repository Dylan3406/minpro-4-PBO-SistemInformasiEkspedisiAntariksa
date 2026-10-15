package controller;

import model.dao.EkspedisiDAOORM;
import java.util.ArrayList;
import java.util.List;
import model.Ekspedisi;
import model.EntitasAntariksa;
import view.View;

public class EkspedisiController {
    private final EkspedisiDAOORM ekspedisiDAO = new EkspedisiDAOORM();
    private final View view;

    public EkspedisiController(View view) {
        this.view = view;
    }

    public void tambahData() {
        view.cetak("\n--- TAMBAH DATA EKSPEDISI ---");

        int id = view.inputIntPositif("ID Ekspedisi: ");
        if (ekspedisiDAO.idSudahAda(id)) {
            view.cetak("ID Ekspedisi sudah digunakan!");
            return;
        }
        String nama = view.inputString("Nama Ekspedisi: ");
        String tujuan = view.inputString("Tujuan: ");
        int durasi = view.inputIntPositif("Durasi (hari): ");
        String status = pilihStatus();

        if (ekspedisiDAO.tambah(new Ekspedisi(id, nama, tujuan, durasi, status))) {
            view.cetak("\nData ekspedisi berhasil ditambahkan!");
        } else {
            view.cetak("\nData ekspedisi gagal ditambahkan.");
        }
    }

    public void tampilkanData() {
        view.cetak("\n--- DATA EKSPEDISI ---");
        List<Ekspedisi> daftar = ekspedisiDAO.getAll();
        if (daftar.isEmpty()) {
            view.cetak("Belum ada data ekspedisi.");
            return;
        }
        for (Ekspedisi e : daftar) {
            view.tampilkanGaris();
            e.tampilkanData();
        }
        view.tampilkanGaris();
    }

    public void ubahStatus() {
        view.cetak("\n--- UBAH STATUS EKSPEDISI ---");
        if (ekspedisiDAO.getAll().isEmpty()) {
            view.cetak("Belum ada data ekspedisi.");
            return;
        }
        int id = view.inputIntPositif("Masukkan ID ekspedisi yang ingin diubah: ");
        Ekspedisi e = cariEkspedisi(id);
        if (e == null) {
            view.cetak("\nID ekspedisi tidak ditemukan.");
            return;
        }
        e.setStatus(pilihStatus());
        if (ekspedisiDAO.ubah(e)) {
            view.cetak("\nData berhasil diubah!");
        } else {
            view.cetak("\nData gagal diubah.");
        }
    }

    public void hapusData() {
        view.cetak("\n--- HAPUS DATA EKSPEDISI ---");
        if (ekspedisiDAO.getAll().isEmpty()) {
            view.cetak("Belum ada data ekspedisi.");
            return;
        }
        int id = view.inputIntPositif("Masukkan ID ekspedisi yang ingin dihapus: ");
        if (cariEkspedisi(id) == null) {
            view.cetak("\nID ekspedisi tidak ditemukan.");
            return;
        }
        if (ekspedisiDAO.hapus(id)) {
            view.cetak("\nData berhasil dihapus!");
        } else {
            view.cetak("\nData gagal dihapus.");
        }
    }

    public List<EntitasAntariksa> getSemuaEntitas() {
        return new ArrayList<>(ekspedisiDAO.getAll());
    }

    public Ekspedisi cariEkspedisi(int id) {
        return ekspedisiDAO.getById(id);
    }

    public Ekspedisi cariEkspedisi(String nama) {
        for (Ekspedisi e : ekspedisiDAO.cariByNama(nama)) {
            if (e.getNamaEkspedisi().equalsIgnoreCase(nama)) {
                return e;
            }
        }
        return null;
    }

    private String pilihStatus() {
        String[] opsi = {"Direncanakan", "Persiapan", "Berlangsung", "Selesai", "Dibatalkan"};

        while (true) {
            view.cetak("\nStatus Ekspedisi:");
            for (int i = 0; i < opsi.length; i++) {
                view.cetak((i + 1) + ". " + opsi[i]);
            }
            int pilihan = view.inputInt("Pilih status: ");
            if (pilihan >= 1 && pilihan <= opsi.length) {
                return opsi[pilihan - 1];
            }
            view.cetak("Pilihan status tidak tersedia!");
        }
    }
}