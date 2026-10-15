package controller;

import java.util.ArrayList;
import java.util.List;
import model.EntitasAntariksa;
import view.View;

public class Ringkasan {

    private final EkspedisiController ekspedisiController;
    private final KruController kruController;
    private final PesawatController pesawatController;
    private final View view;

    public Ringkasan(View view, EkspedisiController ekspedisiController, KruController kruController, PesawatController pesawatController) {
        this.view = view;
        this.ekspedisiController = ekspedisiController;
        this.kruController = kruController;
        this.pesawatController = pesawatController;
    }

    public void tampilkanRingkasanSemua() {
        view.cetak("\n--- RINGKASAN SEMUA DATA (EKSPEDISI, KRU, PESAWAT) ---");

        List<EntitasAntariksa> semuaData = new ArrayList<>();
        semuaData.addAll(ekspedisiController.getSemuaEntitas());
        semuaData.addAll(kruController.getSemuaEntitas());
        semuaData.addAll(pesawatController.getSemuaEntitas());

        if (semuaData.isEmpty()) {
            view.cetak("Belum ada data sama sekali.");
            return;
        }
        for (EntitasAntariksa entitas : semuaData) {
            view.cetak("- " + entitas.getIdentitas());
        }
        view.cetak("\nTotal data: " + semuaData.size());
    }
}
