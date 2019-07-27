package com.example.manajemen_anggota.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.manajemen_anggota.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

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

//    @OnClick(R.id.btnLogout)
//    void logout (){
//        mAuth.getInstance().signOut();
//        Intent i = new Intent(this, LoginActivity.class);
//        startActivity(i);
//        finish();
//    }

}
