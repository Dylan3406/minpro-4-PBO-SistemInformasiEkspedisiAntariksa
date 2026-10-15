package main;

import controller.EkspedisiController;
import controller.KruController;
import controller.PesawatController;
import controller.Ringkasan;
import view.View;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        EkspedisiController ekspedisiController = new EkspedisiController(view);
        KruController kruController = new KruController(view);
        PesawatController pesawatController = new PesawatController(view);
        Ringkasan ringkasanController = new Ringkasan(
                view, ekspedisiController, kruController, pesawatController);
        int pilihan;
        do {
            view.tampilkanHeader("SISTEM PENGELOLAAN EKSPEDISI ANTARIKSA");
            view.cetak("1. Kelola Ekspedisi");
            view.cetak("2. Kelola Kru");
            view.cetak("3. Kelola Pesawat Antariksa");
            view.cetak("4. Ringkasan Semua Data");
            view.cetak("5. Keluar");
            view.cetak("======================================");
            pilihan = view.inputInt("Pilih menu: ");
            switch (pilihan) {
                case 1 -> menuEkspedisi(view, ekspedisiController);
                case 2 -> menuKru(view, kruController);
                case 3 -> menuPesawat(view, pesawatController);
                case 4 -> ringkasanController.tampilkanRingkasanSemua();
                case 5 -> view.cetak("\nProgram selesai. Terima kasih!");
                default -> {
                    view.cetak("\nPilihan menu tidak tersedia!");
                    view.cetak("Silakan pilih menu 1 - 5.");
                }
            }

        } while (pilihan != 5);
    }
    static void menuEkspedisi(View view, EkspedisiController controller) {
        int pilihan;
        do {
            view.tampilkanHeader("          KELOLA EKSPEDISI");
            view.cetak("1. Tambah Data");
            view.cetak("2. Tampilkan Data");
            view.cetak("3. Ubah Status Ekspedisi");
            view.cetak("4. Hapus Data");
            view.cetak("5. Kembali");
            view.cetak("======================================");
            pilihan = view.inputInt("Pilih menu: ");
            switch (pilihan) {
                case 1 -> controller.tambahData();
                case 2 -> controller.tampilkanData();
                case 3 -> controller.ubahStatus();
                case 4 -> controller.hapusData();
                case 5 -> {
                }
                default -> {
                    view.cetak("\nPilihan menu tidak tersedia!");
                    view.cetak("Silakan pilih menu 1 - 5.");
                }
            }
        } while (pilihan != 5);
    }
    static void menuKru(View view, KruController controller) {
        int pilihan;
        do {
            view.tampilkanHeader("KELOLA KRU");
            view.cetak("1. Tambah Data");
            view.cetak("2. Tampilkan Data");
            view.cetak("3. Ubah Data");
            view.cetak("4. Hapus Data");
            view.cetak("5. Kembali");
            view.cetak("======================================");
            pilihan = view.inputInt("Pilih menu: ");
            switch (pilihan) {
                case 1 -> controller.tambahData();
                case 2 -> controller.tampilkanData();
                case 3 -> controller.ubahData();
                case 4 -> controller.hapusData();
                case 5 -> {
                }
                default -> {
                    view.cetak("\nPilihan menu tidak tersedia!");
                    view.cetak("Silakan pilih menu 1 - 5.");
                }
            }
        } while (pilihan != 5);
    }
    static void menuPesawat(View view, PesawatController controller) {
        int pilihan;
        do {
            view.tampilkanHeader("KELOLA PESAWAT ANTARIKSA");
            view.cetak("1. Tambah Data");
            view.cetak("2. Tampilkan Data");
            view.cetak("3. Ubah Data");
            view.cetak("4. Hapus Data");
            view.cetak("5. Kembali");
            view.cetak("======================================");
            pilihan = view.inputInt("Pilih menu: ");
            switch (pilihan) {
                case 1 -> controller.tambahData();
                case 2 -> controller.tampilkanData();
                case 3 -> controller.ubahData();
                case 4 -> controller.hapusData();
                case 5 -> {
                }
                default -> {
                    view.cetak("\nPilihan menu tidak tersedia!");
                    view.cetak("Silakan pilih menu 1 - 5.");
                }
            }
        } while (pilihan != 5);
    }
}
