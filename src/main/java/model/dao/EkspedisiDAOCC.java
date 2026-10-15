package model.dao;

import config.Koneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Ekspedisi;

public class EkspedisiDAOCC {

    private Ekspedisi mapRow(ResultSet rs) throws SQLException {
        return new Ekspedisi(
            rs.getInt("id_ekspedisi"),
            rs.getString("nama"),
            rs.getString("tujuan"),
            rs.getInt("durasi"),
            rs.getString("status"));
    }

    public boolean tambah(Ekspedisi e) {
        String sql = "INSERT INTO ekspedisi (id_ekspedisi, nama, tujuan, durasi, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setInt(1, e.getIdEkspedisi());
            ps.setString(2, e.getNamaEkspedisi());
            ps.setString(3, e.getTujuan());
            ps.setInt(4, e.getDurasi());
            ps.setString(5, e.getStatus());
            return ps.executeUpdate() > 0;
        } 
        catch (SQLException ex) {
            System.out.println("Gagal tambah ekspedisi: " + ex.getMessage());
            return false;
        }
    }

    public List<Ekspedisi> getAll() {
        List<Ekspedisi> list = new ArrayList<>();
        String sql = "SELECT * FROM ekspedisi ORDER BY id_ekspedisi";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException ex) {
            System.out.println("Gagal ambil ekspedisi: " + ex.getMessage());
        }
        return list;
    }

    public Ekspedisi getById(int id) {
        String sql = "SELECT * FROM ekspedisi WHERE id_ekspedisi = ?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Gagal cari ekspedisi: " + ex.getMessage());
        }
        return null;
    }

    public List<Ekspedisi> cariByNama(String nama) {
        List<Ekspedisi> list = new ArrayList<>();
        String sql = "SELECT * FROM ekspedisi WHERE nama LIKE ?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + nama + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Gagal cari ekspedisi: " + ex.getMessage());
        }
        return list;
    }

    public boolean ubah(Ekspedisi e) {
        String sql = "UPDATE ekspedisi SET nama = ?, tujuan = ?, durasi = ?, status = ? WHERE id_ekspedisi = ?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, e.getNamaEkspedisi());
            ps.setString(2, e.getTujuan());
            ps.setInt(3, e.getDurasi());
            ps.setString(4, e.getStatus());
            ps.setInt(5, e.getIdEkspedisi());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Gagal ubah ekspedisi: " + ex.getMessage());
            return false;
        }
    }

    public boolean hapus(int id) {
        String sql = "DELETE FROM ekspedisi WHERE id_ekspedisi = ?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Gagal hapus ekspedisi: " + ex.getMessage());
            return false;
        }
    }

    public boolean idSudahAda(int id) {
        return getById(id) != null;
    }
}