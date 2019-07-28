package com.example.manajemen_anggota.View.Fragment;

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

import com.example.manajemen_anggota.R;
import com.example.manajemen_anggota.View.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends Fragment implements AdapterView.OnItemClickListener {

    private FirebaseAuth mAuth;
    ArrayAdapter<CharSequence> adapter;
    @BindView(R.id.l_view)
    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        mAuth = FirebaseAuth.getInstance();
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.countries_arry,R.layout.item_profile);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (id == 3){
            Toast.makeText(getActivity(), adapter.getItem(position), Toast.LENGTH_SHORT).show();
        }
    }

//    @OnClick(R.id.logout)
//    public void logout(){
//        mAuth.signOut();
//        startActivity(new Intent(getActivity(), LoginActivity.class));
//        getActivity().finish();
//    }
}
