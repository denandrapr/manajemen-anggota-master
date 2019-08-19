package com.example.manajemen_anggota.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manajemen_anggota.Adapter.AnggotaAdapter;
import com.example.manajemen_anggota.Model.Anggota;
import com.example.manajemen_anggota.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlumniAnggotaActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.total_anggota_baru)
    TextView txtTotalAnggotaBaru;
    @BindView(R.id.total_anggota_lama)
    TextView txtAnggotaLama;

    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference anggotaRef = db.collection("Anggota");
    private AnggotaAdapter adapter;
    private ArrayList<Anggota> mArrayList;
    private List<Anggota> mAnggota = new ArrayList<>();
    int data_size = 0;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_anggota);

        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        getData(user.getEmail());
        
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void getData(String email){


        Query q = anggotaRef;

        FirestoreRecyclerOptions<Anggota> options = new FirestoreRecyclerOptions.Builder<Anggota>()
                .setQuery(q, Anggota.class)
                .build();
        adapter = new AnggotaAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.list_data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        count_data();
        count_anggota_lama();
    }

    public void count_data(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        db.collection("Anggota")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()){
                                i += 1;
                            }
                            txtAnggotaLama.setText(Integer.toString(i));
                        }
                    }
                });
    }

    public void count_anggota_lama(){
        db.collection("Anggota")
                .whereEqualTo("angkatan", 18)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()){
                                i += 1;
                            }
                            txtTotalAnggotaBaru.setText(Integer.toString(i));
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    
    @OnClick(R.id.fab)
    void fabAction(){
        Intent i = new Intent(AlumniAnggotaActivity.this, TambahAnggota.class);
        startActivity(i);
    }
}
