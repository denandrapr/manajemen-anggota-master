package com.example.manajemen_anggota.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.manajemen_anggota.Model.ProfileItem;
import com.example.manajemen_anggota.R;

import java.util.List;

public class ProfileListViewAdapter extends BaseAdapter {
    Context context;
    List<ProfileItem> profileItems;

    public ProfileListViewAdapter(Context context, List<ProfileItem> profileItems) {
        this.context = context;
        this.profileItems = profileItems;
    }

    private class ViewHolder{
        TextView isi;
        TextView keterangan;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder vHolder = null;
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_profile, null);
            vHolder = new ViewHolder();
            vHolder.isi = (TextView) convertView.findViewById(R.id.isi);
            vHolder.keterangan = (TextView) convertView.findViewById(R.id.keterangan);
            convertView.setTag(vHolder);
        }else{
            vHolder = (ViewHolder) convertView.getTag();
        }

        ProfileItem profileItem = (ProfileItem) getItem(position);
        vHolder.keterangan.setText(profileItem.getKeterangan());
        vHolder.isi.setText(profileItem.getIsi());

        return convertView;
    }

    @Override
    public int getCount() {
        return profileItems.size();
    }

    @Override
    public Object getItem(int position) {
        return profileItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return profileItems.indexOf(getItem(position));
    }
}
