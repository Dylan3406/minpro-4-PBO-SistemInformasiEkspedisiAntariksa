package model.dao;

import config.Koneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.PesawatAntariksa;

public class PesawatDAOCC {

    private PesawatAntariksa mapRow(ResultSet rs) throws SQLException {
        return new PesawatAntariksa(
            rs.getInt("id_pesawat"),
            rs.getString("nama"),
            rs.getString("jenis"),
            rs.getInt("kapasitas"),
            rs.getString("status"));
    }

    public boolean tambah(PesawatAntariksa p) {
        String sql = "INSERT INTO pesawat_antariksa (id_pesawat, nama, jenis, kapasitas, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setInt(1, p.getIdPesawat());
            ps.setString(2, p.getNamaPesawat());
            ps.setString(3, p.getJenis());
            ps.setInt(4, p.getKapasitas());
            ps.setString(5, p.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Gagal tambah pesawat: " + ex.getMessage());
            return false;
        }
    }

    public List<PesawatAntariksa> getAll() {
        List<PesawatAntariksa> list = new ArrayList<>();
        String sql = "SELECT * FROM pesawat_antariksa ORDER BY id_pesawat";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException ex) {
            System.out.println("Gagal ambil pesawat: " + ex.getMessage());
        }
        return list;
    }

    public PesawatAntariksa getById(int id) {
        String sql = "SELECT * FROM pesawat_antariksa WHERE id_pesawat = ?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Gagal cari pesawat: " + ex.getMessage());
        }
        return null;
    }

    public List<PesawatAntariksa> cariByNama(String nama) {
        List<PesawatAntariksa> list = new ArrayList<>();
        String sql = "SELECT * FROM pesawat_antariksa WHERE nama LIKE ?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + nama + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Gagal cari pesawat: " + ex.getMessage());
        }
        return list;
    }

    public boolean ubah(PesawatAntariksa p) {
        String sql = "UPDATE pesawat_antariksa SET nama = ?, jenis = ?, kapasitas = ?, status = ? WHERE id_pesawat = ?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, p.getNamaPesawat());
            ps.setString(2, p.getJenis());
            ps.setInt(3, p.getKapasitas());
            ps.setString(4, p.getStatus());
            ps.setInt(5, p.getIdPesawat());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Gagal ubah pesawat: " + ex.getMessage());
            return false;
        }
    }

    public boolean hapus(int id) {
        String sql = "DELETE FROM pesawat_antariksa WHERE id_pesawat = ?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Gagal hapus pesawat: " + ex.getMessage());
            return false;
        }
    }

    public boolean idSudahAda(int id) {
        return getById(id) != null;
    }
}