package model.dao;

import config.Koneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Astronot;
import model.Kru;
import model.Teknisi;

public class KruDAOCC {

    private Kru mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_kru");
        String nama = rs.getString("nama");
        int usia = rs.getInt("usia");
        if ("Astronot".equals(rs.getString("tipe"))) {
            return new Astronot(id, nama, usia,
                rs.getString("spesialisasi"), rs.getInt("jam_terbang"));
        }
        return new Teknisi(id, nama, usia,
            rs.getString("bidang_keahlian"), rs.getString("sertifikasi"));
    }

    public boolean tambah(Kru k) {
        String sql = "INSERT INTO kru (id_kru, nama, usia, tipe, spesialisasi, jam_terbang, bidang_keahlian, sertifikasi) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setInt(1, k.getIdKru());
            ps.setString(2, k.getNama());
            ps.setInt(3, k.getUsia());
            ps.setString(4, k.getPeran());
            if (k instanceof Astronot a) {
                ps.setString(5, a.getSpesialisasi());
                ps.setInt(6, a.getJamTerbang());
                ps.setNull(7, Types.VARCHAR);
                ps.setNull(8, Types.VARCHAR);
            } else {
                Teknisi t = (Teknisi) k;
                ps.setNull(5, Types.VARCHAR);
                ps.setNull(6, Types.INTEGER);
                ps.setString(7, t.getBidangKeahlian());
                ps.setString(8, t.getSertifikasi());
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Gagal tambah kru: " + ex.getMessage());
            return false;
        }
    }

    public List<Kru> getAll() {
        List<Kru> list = new ArrayList<>();
        String sql = "SELECT * FROM kru ORDER BY id_kru";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException ex) {
            System.out.println("Gagal ambil kru: " + ex.getMessage());
        }
        return list;
    }

    public Kru getById(int id) {
        String sql = "SELECT * FROM kru WHERE id_kru = ?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Gagal cari kru: " + ex.getMessage());
        }
        return null;
    }

    public List<Kru> cariByNama(String nama) {
        List<Kru> list = new ArrayList<>();
        String sql = "SELECT * FROM kru WHERE nama LIKE ?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + nama + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Gagal cari kru: " + ex.getMessage());
        }
        return list;
    }

    public boolean ubah(Kru k) {
        String sql = "UPDATE kru SET nama = ?, usia = ?, spesialisasi = ?, jam_terbang = ?, "
                   + "bidang_keahlian = ?, sertifikasi = ? WHERE id_kru = ?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, k.getNama());
            ps.setInt(2, k.getUsia());
            if (k instanceof Astronot) {
                Astronot a = (Astronot) k;
                ps.setString(3, a.getSpesialisasi());
                ps.setInt(4, a.getJamTerbang());
                ps.setNull(5, Types.VARCHAR);
                ps.setNull(6, Types.VARCHAR);
            } else {
                Teknisi t = (Teknisi) k;
                ps.setNull(3, Types.VARCHAR);
                ps.setNull(4, Types.INTEGER);
                ps.setString(5, t.getBidangKeahlian());
                ps.setString(6, t.getSertifikasi());
            }
            ps.setInt(7, k.getIdKru());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Gagal ubah kru: " + ex.getMessage());
            return false;
        }
    }

    public boolean hapus(int id) {
        String sql = "DELETE FROM kru WHERE id_kru = ?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Gagal hapus kru: " + ex.getMessage());
            return false;
        }
    }

    public boolean idSudahAda(int id) {
        return getById(id) != null;
    }
}