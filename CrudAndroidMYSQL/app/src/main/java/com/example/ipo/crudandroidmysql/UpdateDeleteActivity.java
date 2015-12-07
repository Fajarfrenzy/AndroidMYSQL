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
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateDeleteActivity extends AppCompatActivity {

    JSONParser jParser = new JSONParser();
    String url_update_karyawan = "http://192.168.56.1/karyawan/update_karyawan.php";
    String url_delete_karyawan = "http://192.168.56.1/karyawan/delete_karyawan.php";

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_ID_KAR = "id_kar";
    public static final String TAG_NAMA_KAR = "nama_kar";
    public static final String TAG_ALAMAT_KAR = "alamat_kar";
    public static final String TAG_EMAIL_KAR = "email_kar";

    EditText TxtNama, TxtAlamat, TxtEmail;
    TextView txtViewId;
    Button updateBtn, deleteBtn;
    String idStr, namaStr, alamatStr, emailStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TxtNama = (EditText) findViewById(R.id.txtNama);
        TxtAlamat = (EditText) findViewById(R.id.txtAlamat);
        TxtEmail = (EditText) findViewById(R.id.txtEmail);
        txtViewId = (TextView) findViewById(R.id.id_karyawan);

        updateBtn = (Button) findViewById(R.id.btnUpdate);
        deleteBtn = (Button) findViewById(R.id.btnDelete);

        Bundle b = getIntent().getExtras();
        String isi_id_kar = b.getString("id_kar");
        String isi_nama_kar = b.getString("nama_kar");
        String isi_alamat_kar = b.getString("alamat_kar");
        String isi_email_kar = b.getString("email_kar");

        TxtNama.setText(isi_nama_kar);
        TxtAlamat.setText(isi_alamat_kar);
        TxtEmail.setText(isi_email_kar);
        txtViewId.setText(isi_id_kar);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idStr = txtViewId.getText().toString();
                namaStr = TxtNama.getText().toString();
                alamatStr = TxtAlamat.getText().toString();
                emailStr = TxtEmail.getText().toString();
                new UpdateKarTask().execute();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idStr = txtViewId.getText().toString();
                new DeleteKarTask().execute();
            }
        });
    }

    class UpdateKarTask extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateDeleteActivity.this);
            pDialog.setMessage("Please wait ..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();

            parameter.add(new BasicNameValuePair(TAG_ID_KAR, idStr));
            parameter.add(new BasicNameValuePair(TAG_NAMA_KAR, namaStr));
            parameter.add(new BasicNameValuePair(TAG_ALAMAT_KAR, alamatStr));
            parameter.add(new BasicNameValuePair(TAG_EMAIL_KAR, emailStr));

            try {
                JSONObject json = jParser.makeHttpRequest(url_update_karyawan, "POST", parameter);

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    return "OK";
                } else {
                    return "FAIL";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Caught";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if (result.equalsIgnoreCase("Exception Caught")) {
                Toast.makeText(UpdateDeleteActivity.this, "Unable to connect to server, please check your internet connection!", Toast.LENGTH_LONG).show();
            } else if (result.equalsIgnoreCase("FAIL")) {
                Toast.makeText(UpdateDeleteActivity.this, "Fail.. Try Again", Toast.LENGTH_LONG).show();
            } else {
                Intent i = null;
                i = new Intent(UpdateDeleteActivity.this, MainActivity.class);
                startActivity(i);
            }
        }
    }

    class DeleteKarTask extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateDeleteActivity.this);
            pDialog.setMessage("Please wait ..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();

            parameter.add(new BasicNameValuePair(TAG_ID_KAR, idStr));

            try {
                JSONObject json = jParser.makeHttpRequest(url_delete_karyawan, "POST", parameter);

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    return "OK";
                } else {
                    return "FAIL";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Caught";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if (result.equalsIgnoreCase("Exception Caught")) {
                Toast.makeText(UpdateDeleteActivity.this, "Unable to connect to server, please check your internet connection!", Toast.LENGTH_LONG).show();
            } else if (result.equalsIgnoreCase("FAIL")) {
                Toast.makeText(UpdateDeleteActivity.this, "Fail.. Try Again", Toast.LENGTH_LONG).show();
            } else {
                Intent i = null;
                i = new Intent(UpdateDeleteActivity.this, MainActivity.class);
                startActivity(i);
            }
        }
    }

}
