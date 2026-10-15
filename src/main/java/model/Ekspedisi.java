package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ekspedisi")
public class Ekspedisi implements EntitasAntariksa {
    @Id
    @Column(name = "id_ekspedisi")
    private int idEkspedisi;

    @Column(name = "nama")
    private String namaEkspedisi;

    private String tujuan;
    private int durasi;
    private String status;

    protected Ekspedisi() {
    }

    public Ekspedisi(int idEkspedisi, String namaEkspedisi,
    String tujuan, int durasi, String status) {
        this.idEkspedisi = idEkspedisi;
        this.namaEkspedisi = namaEkspedisi;
        this.tujuan = tujuan;
        this.durasi = durasi;
        this.status = status;
    }
    public int getIdEkspedisi() {
        return idEkspedisi;
    }
    public void setIdEkspedisi(int idEkspedisi) {
        this.idEkspedisi = idEkspedisi;
    }
    public String getNamaEkspedisi() {
        return namaEkspedisi;
    }
    public void setNamaEkspedisi(String namaEkspedisi) {
        this.namaEkspedisi = namaEkspedisi;
    }
    public String getTujuan() {
        return tujuan;
    }
    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }
    public int getDurasi() {
        return durasi;
    }
    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public void tampilkanData() {
        System.out.println("ID Ekspedisi : " + idEkspedisi);
        System.out.println("Nama         : " + namaEkspedisi);
        System.out.println("Tujuan       : " + tujuan);
        System.out.println("Durasi       : " + durasi + " hari");
        System.out.println("Status       : " + status);
    }
    @Override
    public String getIdentitas() {
        return "[EKSPEDISI] #" + idEkspedisi + " - " + namaEkspedisi + " (" + status + ")";
    }
}