package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pesawat_antariksa")
public class PesawatAntariksa implements EntitasAntariksa {
    @Id
    @Column(name = "id_pesawat")
    private int idPesawat;

    @Column(name = "nama")
    private String namaPesawat;

    private String jenis;
    private int kapasitas;
    private String status;

    protected PesawatAntariksa() {
    }

    public PesawatAntariksa(int idPesawat, String namaPesawat,
    String jenis, int kapasitas, String status) {
        this.idPesawat = idPesawat;
        this.namaPesawat = namaPesawat;
        this.jenis = jenis;
        this.kapasitas = kapasitas;
        this.status = status;
    }
    public int getIdPesawat() {
        return idPesawat;
    }
    public void setIdPesawat(int idPesawat) {
        this.idPesawat = idPesawat;
    }
    public String getNamaPesawat() {
        return namaPesawat;
    }
    public void setNamaPesawat(String namaPesawat) {
        this.namaPesawat = namaPesawat;
    }
    public String getJenis() {
        return jenis;
    }
    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
    public int getKapasitas() {
        return kapasitas;
    }
    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public void tampilkanData() {
        System.out.println("ID Pesawat   : " + idPesawat);
        System.out.println("Nama         : " + namaPesawat);
        System.out.println("Jenis        : " + jenis);
        System.out.println("Kapasitas    : " + kapasitas + " orang");
        System.out.println("Status       : " + status);
    }
    @Override
    public String getIdentitas() {
        return "[PESAWAT] #" + idPesawat + " - " + namaPesawat + " (" + status + ")";
    }
}