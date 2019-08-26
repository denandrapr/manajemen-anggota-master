package com.example.manajemen_anggota.View.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.manajemen_anggota.Adapter.ProfileListViewAdapter;
import com.example.manajemen_anggota.Model.Anggota;
import com.example.manajemen_anggota.Model.ProfileItem;
import com.example.manajemen_anggota.R;
import com.example.manajemen_anggota.View.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends Fragment{

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference anggotaRef = db.collection("Anggota");

    @BindView(R.id.name)
    TextView txtName;
    @BindView(R.id.imageView4)
    ImageView imgProfile;
    @BindView(R.id.txtIsiNim)
    TextView txtNim;
    @BindView(R.id.txtIsiNama)
    TextView txtNama;
    @BindView(R.id.txtIsiProdi)
    TextView txtProdi;
    @BindView(R.id.txtIsiTelp)
    TextView txtTelp;
    @BindView(R.id.txtIsiLine)
    TextView txtLine;

    ProgressDialog progressDialog;

    private void get_data_profile(){
        String uid = "";
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if (user != null){
            uid = user.getEmail();
        }
        db.collection("Anggota")
                .document(uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        progressDialog.dismiss();
                        Anggota anggota = null;
                        anggota = documentSnapshot.toObject(Anggota.class);
                        Glide.with(getActivity())
                                .load(anggota.getGambar())
                                .circleCrop()
                                .placeholder(R.drawable.placeholder)
                                .into(imgProfile);
                        txtName.setText(anggota.getNama());
                        txtNim.setText(anggota.getNim());
                        txtNama.setText(anggota.getNama());
                        txtProdi.setText(anggota.getProdi());
                        txtTelp.setText(anggota.getNoTelp());
                        txtLine.setText(anggota.getIdLine());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Load data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        get_data_profile();
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if (user != null){
            String uid = user.getEmail();
        }
        return view;
    }

    @OnClick(R.id.logout)
    void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }

//    @OnClick(R.id.logout)
//    public void logout(){
//        mAuth.signOut();
//        startActivity(new Intent(getActivity(), LoginActivity.class));
//        getActivity().finish();
//    }
}
