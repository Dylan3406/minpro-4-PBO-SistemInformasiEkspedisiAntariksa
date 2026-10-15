package model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Astronot")
public class Astronot extends Kru {
    private String spesialisasi;

    @Column(name = "jam_terbang")
    private int jamTerbang;

    protected Astronot() {
    }

    public Astronot(int idKru, String nama, int usia, String spesialisasi, int jamTerbang) {
        super(idKru, nama, usia);
        this.spesialisasi = spesialisasi;
        this.jamTerbang = jamTerbang;
    }
    public String getSpesialisasi() {
        return spesialisasi;
    }
    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }
    public int getJamTerbang() {
        return jamTerbang;
    }
    public void setJamTerbang(int jamTerbang) {
        this.jamTerbang = jamTerbang;
    }
    @Override
    public String getPeran() {
        return "Astronot";
    }
    @Override
    public String getDetailTugas() {
        return "Spesialisasi " + spesialisasi + ", Jam Terbang " + jamTerbang + " jam";
    }
}