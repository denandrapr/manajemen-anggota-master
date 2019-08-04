package com.example.manajemen_anggota.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manajemen_anggota.Model.Anggota;
import com.example.manajemen_anggota.R;

import java.util.List;

public class AnggotaAdapter extends RecyclerView.Adapter<AnggotaAdapter.ViewHolder> {
    Context context;
    List<Anggota> anggota;

    public AnggotaAdapter(Context context, List<Anggota> anggota) {
        this.context = context;
        this.anggota = anggota;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anggota, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Anggota mAnggota = anggota.get(position);

        holder.nim.setText(mAnggota.getNim());
    }

    @Override
    public int getItemCount() {
        return anggota.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nim;
//        public TextView StudentNumberTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nim = (TextView) itemView.findViewById(R.id.txtNim);
        }
    }
}
