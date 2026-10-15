package config;

import java.sql.Connection;

public class TesKoneksi {
    public static void main(String[] args){
        System.out.println("Tes Koneksi Minpro4");
        Connection conn = Koneksi.getConnection();
        
        if(conn != null ){
            System.out.println("Koneksi Berhasil");
        }else{
            System.out.println("Koneksi Gagal");
        }
    }
}