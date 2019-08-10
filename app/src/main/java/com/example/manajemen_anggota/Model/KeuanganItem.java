package com.example.manajemen_anggota.Model;

public class KeuanganItem {
    private String judul;
    private String jumlah;
    private String kategori;
    private String keterangan;
    private String tanggal;

    public KeuanganItem() {
    }

    public KeuanganItem(String judul, String jumlah, String kategori, String keterangan, String tanggal) {
        this.judul = judul;
        this.jumlah = jumlah;
        this.kategori = kategori;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
    }

    public String getJudul() {
        return judul;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getKategori() {
        return kategori;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getTanggal() {
        return tanggal;
    }
}
