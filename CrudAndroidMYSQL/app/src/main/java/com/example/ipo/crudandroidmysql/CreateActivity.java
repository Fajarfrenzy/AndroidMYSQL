package com.example.ipo.crudandroidmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CreateActivity extends AppCompatActivity {

    JSONParser jParser = new JSONParser();
    String url_create_karyawan = "http://192.168.56.1/karyawan/create_karyawan.php";

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_NAMA_KAR = "nama_kar";
    public static final String TAG_ALAMAT_KAR = "alamat_kar";
    public static final String TAG_EMAIL_KAR = "email_kar";

    EditText TxtNama, TxtAlamat, TxtEmail;
    Button addBtn;
    String namaStr, alamatStr, emailStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TxtNama = (EditText) findViewById(R.id.txtNama);
        TxtAlamat = (EditText) findViewById(R.id.txtAlamat);
        TxtEmail = (EditText) findViewById(R.id.txtEmail);
        addBtn = (Button) findViewById(R.id.btnAdd);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namaStr = TxtNama.getText().toString();
                alamatStr = TxtAlamat.getText().toString();
                emailStr = TxtEmail.getText().toString();
                new CreateKarTask().execute();
            }
        });
    }

    class CreateKarTask extends AsyncTask<String, String, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(CreateActivity.this);
            dialog.setMessage("Add Karyawan...");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair(TAG_NAMA_KAR, namaStr));
            params.add(new BasicNameValuePair(TAG_ALAMAT_KAR, alamatStr));
            params.add(new BasicNameValuePair(TAG_EMAIL_KAR, emailStr));

            JSONObject json = jParser.makeHttpRequest(url_create_karyawan, "POST", params);
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    finish();
                } else {
                    return "Gagal Database";
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return "Gagal Connect or Exception";
            }
            return "Sukses";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equalsIgnoreCase("Gagal Database")) {
                Toast.makeText(CreateActivity.this, "Terjadi Masalah, please check your internet connection!", Toast.LENGTH_SHORT).show();
            } else if (result.equalsIgnoreCase("Gagal Connect or Exception")) {
                Toast.makeText(CreateActivity.this, "Terjadi Masalah, please check your internet connection!", Toast.LENGTH_SHORT).show();
            } else if (result.equalsIgnoreCase("Sukses")) {
                dialog.dismiss();
                Intent i = null;
                i = new Intent(CreateActivity.this, MainActivity.class);
                startActivity(i);
            }
        }
    }

}
