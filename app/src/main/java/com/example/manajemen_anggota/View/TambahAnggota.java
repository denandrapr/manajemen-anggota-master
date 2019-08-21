package com.example.manajemen_anggota.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.manajemen_anggota.Model.Anggota;
import com.example.manajemen_anggota.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TambahAnggota extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.s_prodi)
    Spinner sProdi;
    @BindView(R.id.imageView3)
    ImageView pic;
    @BindView(R.id.txt_nim)
    EditText txtNim;
    @BindView(R.id.txt_nama)
    EditText txtNama;
    @BindView(R.id.txt_line)
    EditText txtLine;
    @BindView(R.id.txt_hp)
    EditText txtHp;
    @BindView(R.id.jenkel)
    RadioGroup rgJenkel;

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_INTENT = 2;
    private FirebaseFirestore db;

    private RadioButton rb_jenkel;
    private StorageReference mRef;
    ProgressDialog progressDialog;
    String mCurrentPhotoPath;
    String spinner_prodi = "";
    String imageUrl;
    Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_anggota);
        ButterKnife.bind(this);
        mRef = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.prodi, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sProdi.setAdapter(adapter);
        sProdi.setOnItemSelectedListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinner_prodi = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    
    @OnClick(R.id.img_camera)
    void camera_action(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.manajemen_anggota",
                        photoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(i, CAMERA_REQUEST_CODE);
            }
        }
    }

    @OnClick(R.id.img_storage)
    void storage_action(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            Glide.with(this)
                    .load(photoURI)
                    .circleCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(pic);
        }else if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK && data != null && data.getData() != null){
            photoURI = data.getData();
            Glide.with(this)
                    .load(photoURI)
                    .circleCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(pic);
        }
    }

    @OnClick(R.id.btn_tambah)
    void tambah_anggota(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Prosessing...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String nim = txtNim.getText().toString();
        String nama = txtNama.getText().toString();
        String line = txtLine.getText().toString();
        String noHp = txtHp.getText().toString();
        String jenkel = "";
        int selectedId = rgJenkel.getCheckedRadioButtonId();
        rb_jenkel = (RadioButton) findViewById(selectedId);
        if (rb_jenkel.getText().toString().equals("Perempuan")){
            jenkel = "P";
        }else{
            jenkel = "L";
        }
        String angkatan = nim.substring(0,2);
        doUpload(nim, nama, line, noHp, jenkel, spinner_prodi, angkatan);
    }

    private void doUpload(String nim, String nama, String line, String noHp, String jenkel, String spinner_prodi, String angkatan) {
        final String[] url = new String[1];
        if (photoURI != null) {
            StorageReference ref = mRef.child("Profile/" + System.currentTimeMillis());
            UploadTask uploadTask = ref.putFile(photoURI);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Uri downloadUrl = task.getResult();

                        Anggota anggota = new Anggota(jenkel, nama, nim, spinner_prodi, downloadUrl.toString(), noHp, line, angkatan);
                        db.collection("Anggota").document(nim+"@stikom.edu").set(anggota);

                        Intent i = new Intent(TambahAnggota.this, AlumniAnggotaActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        StyleableToast.makeText(TambahAnggota.this,"Sukses", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(TambahAnggota.this, "gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Anggota anggota = new Anggota(jenkel, nama, nim, spinner_prodi, "no image", noHp, line, angkatan);
            db.collection("Anggota").document(nim+"@stikom.edu").set(anggota);
            Intent i = new Intent(TambahAnggota.this, AlumniAnggotaActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }
}
