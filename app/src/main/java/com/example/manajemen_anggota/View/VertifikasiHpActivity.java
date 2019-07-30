package com.example.manajemen_anggota.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.manajemen_anggota.R;
import com.example.manajemen_anggota.View.LoginActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.os.Build.ID;

public class VertifikasiHpActivity extends AppCompatActivity {

    @BindView(R.id.nomerHp)
    EditText nomerHape;

    ProgressDialog progress;
    private FirebaseAuth mAuth;
    String codesent;
    final String PHONE_CODE = "+62";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertifikasi_hp);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.buttonVerification)
    void getVerification(){
//        progress = new ProgressDialog(VertifikasiHpActivity.this);
//        progress.setCancelable(false);
//        progress.setMessage("Mengirim kode...");
//        progress.show();
        String phoneNumber = PHONE_CODE+nomerHape.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        mAuth.setLanguageCode(ID);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Log.d("tag ", phoneAuthCredential.toString());
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.d("tagg ", s);
            codesent = s;
        }
    };
}
