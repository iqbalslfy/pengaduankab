package com.mascitra.android.pengaduanlmj.adapter;

/**
 * Created by SONY on 06/09/2017.
 */

public class Data {
    private String nama,ktp,kecamatan,kelurahan,rt,rw,alamat,email,dkpd,keluhan;

    public Data(String nama, String ktp, String kecamatan, String kelurahan, String rt, String rw, String alamat, String email, String dkpd, String keluhan) {
        this.nama = nama;
        this.ktp = ktp;
        this.kecamatan = kecamatan;
        this.kelurahan = kelurahan;
        this.rt = rt;
        this.rw = rw;
        this.alamat = alamat;
        this.email = email;
        this.dkpd = dkpd;
        this.keluhan = keluhan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDkpd() {
        return dkpd;
    }

    public void setDkpd(String dkpd) {
        this.dkpd = dkpd;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }
}
