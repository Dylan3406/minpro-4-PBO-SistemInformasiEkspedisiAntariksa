package view;

import java.util.Scanner;
public class View {
    private final Scanner input = new Scanner(System.in);
    public void cetak(String pesan) {
        System.out.println(pesan);
    }
    public void tampilkanHeader(String judul) {
        cetak("\n======================================");
        cetak(judul);
        cetak("======================================");
    }
    public void tampilkanGaris() {
        cetak("--------------------------------------");
    }
    public int inputInt(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                cetak("Input tidak valid! Data harus berupa angka.");
            }
        }
    }
    public int inputIntPositif(String pesan) {
        while (true) {
            int angka = inputInt(pesan);
            if (angka > 0) {
                return angka;
            }
            cetak("Input tidak valid! Nilai harus lebih dari 0.");
        }
    }
    public int inputIntNonNegatif(String pesan) {
        while (true) {
            int angka = inputInt(pesan);
            if (angka >= 0) {
                return angka;
            }
            cetak("Input tidak valid! Nilai tidak boleh negatif.");
        }
    }
    public String inputString(String pesan) {
        while (true) {
            System.out.print(pesan);
            String data = input.nextLine();
            if (!data.trim().isEmpty()) {
                return data;
            }
            cetak("Input tidak boleh kosong!");
        }
    }
}