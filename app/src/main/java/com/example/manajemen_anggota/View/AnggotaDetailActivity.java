package com.example.manajemen_anggota.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.manajemen_anggota.Model.Anggota;
import com.example.manajemen_anggota.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnggotaDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageView3)
    ImageView imgProfile;
    @BindView(R.id.dataNim)
    TextView txtToolbarNim;
    @BindView(R.id.s_prodi)
    EditText sProdi;
    @BindView(R.id.txt_nim)
    EditText txtNim;
    @BindView(R.id.txt_nama)
    EditText txtNama;
    @BindView(R.id.txt_line)
    EditText txtLine;
    @BindView(R.id.txt_hp)
    EditText txtHp;
    @BindView(R.id.jenkel)
    EditText jenkel;

    String nim = "";
    ProgressDialog progressDialog;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference anggotaRef = db.collection("Anggota");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggota_detail);

        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        nim = i.getStringExtra("PassNim");
        txtToolbarNim.setText(nim);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Prosessing...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        get_data_profile();
    }

    private void get_data_profile(){
        db.collection("Anggota")
                .document(nim+"@stikom.edu")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        progressDialog.dismiss();
                        Anggota anggota = null;
                        anggota = documentSnapshot.toObject(Anggota.class);
                        Glide.with(AnggotaDetailActivity.this)
                                .load(anggota.getGambar())
                                .circleCrop()
                                .placeholder(R.drawable.placeholder)
                                .into(imgProfile);
                        txtNama.setText(anggota.getNama());
                        txtNim.setText(anggota.getNim());
                        txtLine.setText(anggota.getIdLine());
                        txtHp.setText(anggota.getNoTelp());
                        sProdi.setText(anggota.getProdi());
                        if (anggota.getJenkel().equals("L")){
                            jenkel.setText("Laki - Laki");
                        }else{
                            jenkel.setText("Perempuan");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AnggotaDetailActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
