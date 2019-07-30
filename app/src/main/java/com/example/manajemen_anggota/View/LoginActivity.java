package com.example.manajemen_anggota.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manajemen_anggota.R;
import com.example.manajemen_anggota.VertifikasiHpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @BindView(R.id.txtNim)
    EditText inputNim;
    @BindView(R.id.txtPass)
    EditText inputPass;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    private ProgressDialog progress;
    String usernm, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);
    }

    @OnClick (R.id.btnLogin)
    void login(){
        startActivity(new Intent(this, VertifikasiHpActivity.class));
        progress = new ProgressDialog(LoginActivity.this);
        progress.setCancelable(false);
        progress.setMessage("Proses Login...");
        progress.show();

        usernm = inputNim.getText().toString()+"@stikom.edu";
        pass = inputPass.getText().toString();

        mAuth.signInWithEmailAndPassword(usernm, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progress.dismiss();
                            Intent i = new Intent(LoginActivity.this, VertifikasiHpActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            progress.dismiss();
                            Toast.makeText(LoginActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
