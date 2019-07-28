package com.example.manajemen_anggota.Model;

public class ProfileItem {
    private String isi;
    private String keterangan;

    public ProfileItem(String isi, String keterangan) {
        this.isi = isi;
        this.keterangan = keterangan;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
