package com.example.manajemen_anggota.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manajemen_anggota.Model.Anggota;
import com.example.manajemen_anggota.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AnggotaAdapter extends FirestoreRecyclerAdapter<Anggota, AnggotaAdapter.ViewHolder> {

    public AnggotaAdapter(@NonNull FirestoreRecyclerOptions<Anggota> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Anggota anggota) {
        viewHolder.txtNim.setText(anggota.getNim());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anggota_alumni, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNim;
        public ViewHolder(View itemView){
            super(itemView);
            txtNim = itemView.findViewById(R.id.nim);
        }

    }
}
