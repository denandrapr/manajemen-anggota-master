package com.example.manajemen_anggota.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.manajemen_anggota.View.AnggotaDetailActivity;
import com.example.manajemen_anggota.Model.Anggota;
import com.example.manajemen_anggota.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnggotaAdapter extends FirestoreRecyclerAdapter<Anggota, AnggotaAdapter.ViewHolder> {

    Context context;

    public AnggotaAdapter(@NonNull FirestoreRecyclerOptions<Anggota> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Anggota anggota) {
        viewHolder.txtNim.setText(anggota.getNim());
        viewHolder.txtNama.setText(anggota.getNama());
        String url = anggota.getGambar();
        Glide.with(viewHolder.profile_pic.getContext())
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.profile_pic);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anggota_alumni, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        TextView txtNim;
        @BindView(R.id.nim)
        TextView txtNim;
        @BindView(R.id.nama)
        TextView txtNama;
        @BindView(R.id.profile_pic)
        ImageView profile_pic;
        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
//            txtNim = itemView.findViewById(R.id.nim);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String nim = txtNim.getText().toString();
            Intent i = new Intent(view.getContext(), AnggotaDetailActivity.class);
            i.putExtra("PassNim", nim);
            view.getContext().startActivity(i);
//            Toast.makeText(view.getContext(), "Nim : "+nim, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(FirebaseFirestoreException e) {
        Log.e("error", e.getMessage());
    }
}
