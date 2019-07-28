package com.example.manajemen_anggota.View.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.manajemen_anggota.Adapter.ProfileListViewAdapter;
import com.example.manajemen_anggota.Model.ProfileItem;
import com.example.manajemen_anggota.R;
import com.example.manajemen_anggota.View.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends Fragment implements AdapterView.OnItemClickListener {

    private FirebaseAuth mAuth;

    @BindView(R.id.l_view)
    ListView mListView;

    public static final String[] isi = new String[]{
            "174101000021",
            "Denandra Prasetya Laksma Putra",
            "17410100021@stikom.edu",
            ""
    };
    public static final String[] keterangan = new String[]{
            "Nim",
            "Nama",
            "Email",
            "Logout"
    };
    List<ProfileItem> profileItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        mAuth = FirebaseAuth.getInstance();
        profileItems = new ArrayList<ProfileItem>();
        for (int i = 0; i < isi.length; i++){
            ProfileItem item = new ProfileItem(isi[i], keterangan[i]);
            profileItems.add(item);
        }
        ProfileListViewAdapter adapter = new ProfileListViewAdapter(getActivity(), profileItems);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (id == 3){
            new AlertDialog.Builder(getActivity())
                    .setTitle("Logout")
                    .setMessage("Apa anda yakin ingin logout?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            mAuth.signOut();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }
    }

//    @OnClick(R.id.logout)
//    public void logout(){
//        mAuth.signOut();
//        startActivity(new Intent(getActivity(), LoginActivity.class));
//        getActivity().finish();
//    }
}
