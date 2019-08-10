package com.example.manajemen_anggota.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.manajemen_anggota.View.AlumniAnggotaActivity;
import com.example.manajemen_anggota.R;
import com.example.manajemen_anggota.View.DokumentasiActivity;
import com.example.manajemen_anggota.View.KeuanganActivity;
import com.example.manajemen_anggota.View.ProkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        return view;
    }

    @OnClick(R.id.box1)
    void Kas(){
        Intent i = new Intent(getActivity(), KeuanganActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.box2)
    void AlumniAnggota(){
        Intent i = new Intent(getActivity(), AlumniAnggotaActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.box3)
    void Proker(){
        Intent i = new Intent(getActivity(), ProkerActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.box4)
    void Dokumentasi(){
        Intent i = new Intent(getActivity(), DokumentasiActivity.class);
        startActivity(i);
    }

}
