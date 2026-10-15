package controller;

import model.dao.PesawatDAOORM;
import java.util.ArrayList;
import java.util.List;
import model.EntitasAntariksa;
import model.PesawatAntariksa;
import view.View;

public class PesawatController {
    private final PesawatDAOORM pesawatDAO = new PesawatDAOORM();
    private final View view;

    public PesawatController(View view) {
        this.view = view;
    }

    public void tambahData() {
        view.cetak("\n--- TAMBAH DATA PESAWAT ---");

        int id = view.inputIntPositif("ID Pesawat: ");
        if (pesawatDAO.idSudahAda(id)) {
            view.cetak("ID Pesawat sudah digunakan!");
            return;
        }
        String nama = view.inputString("Nama Pesawat: ");
        String jenis = view.inputString("Jenis: ");
        int kapasitas = view.inputIntPositif("Kapasitas (orang): ");
        String status = pilihStatus();

        if (pesawatDAO.tambah(new PesawatAntariksa(id, nama, jenis, kapasitas, status))) {
            view.cetak("\nData pesawat berhasil ditambahkan!");
        } else {
            view.cetak("\nData pesawat gagal ditambahkan.");
        }
    }

    public void tampilkanData() {
        view.cetak("\n--- DATA PESAWAT ANTARIKSA ---");
        List<PesawatAntariksa> daftar = pesawatDAO.getAll();
        if (daftar.isEmpty()) {
            view.cetak("Belum ada data pesawat.");
            return;
        }
        for (PesawatAntariksa p : daftar) {
            view.tampilkanGaris();
            p.tampilkanData();
        }
        view.tampilkanGaris();
    }

    public void ubahData() {
        view.cetak("\n--- UBAH DATA PESAWAT ---");
        if (pesawatDAO.getAll().isEmpty()) {
            view.cetak("Belum ada data pesawat.");
            return;
        }
        int id = view.inputIntPositif("Masukkan ID pesawat yang ingin diubah: ");
        PesawatAntariksa p = cariPesawat(id);
        if (p == null) {
            view.cetak("\nID pesawat tidak ditemukan.");
            return;
        }
        p.setNamaPesawat(view.inputString("Nama baru: "));
        p.setJenis(view.inputString("Jenis baru: "));
        p.setKapasitas(view.inputIntPositif("Kapasitas baru: "));
        p.setStatus(pilihStatus());

        if (pesawatDAO.ubah(p)) {
            view.cetak("\nData berhasil diubah!");
        } else {
            view.cetak("\nData gagal diubah.");
        }
    }

    public void hapusData() {
        view.cetak("\n--- HAPUS DATA PESAWAT ---");
        if (pesawatDAO.getAll().isEmpty()) {
            view.cetak("Belum ada data pesawat.");
            return;
        }
        int id = view.inputIntPositif("Masukkan ID pesawat yang ingin dihapus: ");
        if (cariPesawat(id) == null) {
            view.cetak("\nID pesawat tidak ditemukan.");
            return;
        }
        if (pesawatDAO.hapus(id)) {
            view.cetak("\nData berhasil dihapus!");
        } else {
            view.cetak("\nData gagal dihapus.");
        }
    }

    public List<EntitasAntariksa> getSemuaEntitas() {
        return new ArrayList<>(pesawatDAO.getAll());
    }

    public PesawatAntariksa cariPesawat(int id) {
        return pesawatDAO.getById(id);
    }

    public PesawatAntariksa cariPesawat(String nama) {
        for (PesawatAntariksa p : pesawatDAO.cariByNama(nama)) {
            if (p.getNamaPesawat().equalsIgnoreCase(nama)) {
                return p;
            }
        }
        return null;
    }

    private String pilihStatus() {
        String[] opsi = {"Siap", "Digunakan", "Perawatan", "Tidak Aktif"};
        while (true) {
            view.cetak("\nStatus Pesawat:");
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