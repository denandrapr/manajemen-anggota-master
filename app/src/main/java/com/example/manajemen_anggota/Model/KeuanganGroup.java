package com.example.manajemen_anggota.Model;

import java.util.ArrayList;

public class KeuanganGroup {
    private String date;
    private ArrayList<KeuanganItem> keuanganItems;

    public KeuanganGroup() {
    }

    public KeuanganGroup(String date, ArrayList<KeuanganItem> keuanganItems) {
        this.date = date;
        this.keuanganItems = keuanganItems;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<KeuanganItem> getKeuanganItems() {
        return keuanganItems;
    }
}
