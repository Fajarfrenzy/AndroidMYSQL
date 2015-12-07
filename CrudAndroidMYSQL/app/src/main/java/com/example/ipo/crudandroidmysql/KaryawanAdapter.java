package com.example.ipo.crudandroidmysql;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ipo on 01/12/15.
 */
public class KaryawanAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Karyawan> data_karyawan = new ArrayList<Karyawan>();

    private static LayoutInflater inflater = null;

    public KaryawanAdapter(Activity a,  ArrayList<Karyawan> d) {
        activity = a; data_karyawan = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data_karyawan.size();
    }

    @Override
    public Object getItem(int position) {
        return data_karyawan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.list_item, null);
        }

        TextView id_karyawan = (TextView) vi.findViewById(R.id.id_karyawan);
        TextView nama_karyawan = (TextView) vi.findViewById(R.id.nama_karyawan);
        TextView alamat_karyawan = (TextView) vi.findViewById(R.id.alamat_karyawan);
        TextView email_karyawan = (TextView) vi.findViewById(R.id.email_karyawan);

        Karyawan daftar_karyawan = data_karyawan.get(position);
        id_karyawan.setText(daftar_karyawan.getId_kar());
        nama_karyawan.setText(daftar_karyawan.getNama_kar());
        alamat_karyawan.setText(daftar_karyawan.getAlamat_kar());
        email_karyawan.setText(daftar_karyawan.getEmail_kar());

        return vi;
    }
}
