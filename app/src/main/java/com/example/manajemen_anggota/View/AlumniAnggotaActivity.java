package com.example.manajemen_anggota.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.manajemen_anggota.Adapter.AnggotaAdapter;
import com.example.manajemen_anggota.Model.Anggota;
import com.example.manajemen_anggota.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

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

    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference anggotaRef = db.collection("Anggota");
    private AnggotaAdapter adapter;
    private ArrayList<Anggota> mArrayList;
    private List<Anggota> mAnggota = new ArrayList<>();

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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Query q = anggotaRef;

        FirestoreRecyclerOptions<Anggota> options = new FirestoreRecyclerOptions.Builder<Anggota>()
                .setQuery(q, Anggota.class)
                .build();
        progressDialog.dismiss();
        adapter = new AnggotaAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.list_data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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
        
    }
}
