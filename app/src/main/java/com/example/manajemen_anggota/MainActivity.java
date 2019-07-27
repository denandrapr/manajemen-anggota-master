package com.example.manajemen_anggota;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @BindView(R.id.btnLogout)
    Button logout;
    @BindView(R.id.textView)
    TextView txtUser;

    @Override
    public void onStart() {
        super.onStart();
//        mAuth.addAuthStateListener(authStateListener);
        mUser = mAuth.getCurrentUser();
        if (mUser == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }else{

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.btnLogout)
    void logout (){
        mAuth.getInstance().signOut();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

}
