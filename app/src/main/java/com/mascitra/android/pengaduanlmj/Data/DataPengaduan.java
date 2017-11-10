package com.mascitra.android.pengaduanlmj.Data;

/**
 * Created by SONY on 04/11/2017.
 */

public class DataPengaduan {

    private int id;
    private String imageLink, nik, nama, tanggal, komentar;

    public DataPengaduan() {
    }

    public DataPengaduan(int id, String imageLink, String nik, String nama, String tanggal, String komentar) {
        this.id = id;
        this.imageLink = imageLink;
        this.nik = nik;
        this.nama = nama;
        this.tanggal = tanggal;
        this.komentar = komentar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
}
