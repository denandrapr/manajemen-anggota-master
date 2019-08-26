package com.example.manajemen_anggota.Model;

public class Anggota {
    private String Jenkel;
    private String Nama;
    private String Nim;
    private String Prodi;
    private String Gambar;
    private String NoTelp;
    private String idLine;
    private String angkatan;
    private String time;

    public Anggota() {
        //oke
    }

    public Anggota(String jenkel, String nama, String nim, String prodi, String gambar, String noTelp, String IdLine, String Angkatan, String Time) {
        Jenkel = jenkel;
        Nama = nama;
        Nim = nim;
        Prodi = prodi;
        Gambar = gambar;
        NoTelp = noTelp;
        idLine = IdLine;
        angkatan = Angkatan;
        time = Time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public String getNoTelp() {
        return NoTelp;
    }

    public String getIdLine() {
        return idLine;
    }

    public String getGambar() {
        return Gambar;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }

    public void setJenkel(String jenkel) {
        Jenkel = jenkel;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public void setNim(String nim) {
        Nim = nim;
    }

    public void setProdi(String prodi) {
        Prodi = prodi;
    }

    public String getJenkel() {
        return Jenkel;
    }

    public String getNama() {
        return Nama;
    }

    public String getNim() {
        return Nim;
    }

    public String getProdi() {
        return Prodi;
    }
}
