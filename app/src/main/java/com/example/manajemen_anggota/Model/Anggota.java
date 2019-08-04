package com.example.manajemen_anggota.Model;

public class Anggota {
    private String Jenkel;
    private String Nama;
    private String Nim;
    private String Prodi;
    private String Key;

    public Anggota() {
    }

    public Anggota(String key, String jenkel, String nama, String nim, String prodi) {
        Key = key;
        Jenkel = jenkel;
        Nama = nama;
        Nim = nim;
        Prodi = prodi;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
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
