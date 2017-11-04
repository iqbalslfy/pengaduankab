package com.mascitra.android.pengaduanlmj.adapter;

/**
 * Created by SONY on 04/11/2017.
 */

public class DataPengaduan {

    private int ImageID;
    private String imageLink, nik, nama, tanggal, komentar;

    public DataPengaduan() {
    }

    public DataPengaduan(int imageID, String imageLink, String nik, String nama, String tanggal, String komentar) {
        ImageID = imageID;
        this.imageLink = imageLink;
        this.nik = nik;
        this.nama = nama;
        this.tanggal = tanggal;
        this.komentar = komentar;
    }

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
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
