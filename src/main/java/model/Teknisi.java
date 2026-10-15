package model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Teknisi")
public class Teknisi extends Kru {
    @Column(name = "bidang_keahlian")
    private String bidangKeahlian;

    private String sertifikasi;

    protected Teknisi() {
    }

    public Teknisi(int idKru, String nama, int usia, String bidangKeahlian, String sertifikasi) {
        super(idKru, nama, usia);
        this.bidangKeahlian = bidangKeahlian;
        this.sertifikasi = sertifikasi;
    }
    public String getBidangKeahlian() {
        return bidangKeahlian;
    }
    public void setBidangKeahlian(String bidangKeahlian) {
        this.bidangKeahlian = bidangKeahlian;
    }
    public String getSertifikasi() {
        return sertifikasi;
    }
    public void setSertifikasi(String sertifikasi) {
        this.sertifikasi = sertifikasi;
    }
    @Override
    public String getPeran() {
        return "Teknisi";
    }
    @Override
    public String getDetailTugas() {
        return "Bidang " + bidangKeahlian + ", Sertifikasi " + sertifikasi;
    }
}