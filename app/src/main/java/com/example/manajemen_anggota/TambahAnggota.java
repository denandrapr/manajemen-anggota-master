package com.example.manajemen_anggota;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_INTENT = 2;

    private StorageReference mRef;
    ProgressDialog progressDialog;
    String mCurrentPhotoPath;
    Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_anggota);
        ButterKnife.bind(this);
        mRef = FirebaseStorage.getInstance().getReference();

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
        String item = adapterView.getItemAtPosition(i).toString();
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
//        if (i.resolveActivity(getPackageManager()) != null){
//            startActivityForResult(i, CAMERA_REQUEST_CODE);
//        }

        if (i.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(i, CAMERA_REQUEST_CODE);
            }
        }

        startActivityForResult(i, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            progressDialog.dismiss();
//            Uri uriPhoto = data.getData();
//            Bundle extras = data.getExtras();
            Bitmap img = (Bitmap) data.getExtras().get("data");
//            Log.d("logg", img.toString());
            Glide.with(this)
                    .load(img)
                    .circleCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(pic);
        }else if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK && data != null && data.getData() != null){
            progressDialog.dismiss();
            photoURI = data.getData();
            Glide.with(this)
                    .load(photoURI)
                    .circleCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(pic);
        }
    }

    @OnClick(R.id.img_storage)
    void storage_action(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, GALLERY_INTENT);
    }
}
