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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView list;

    JSONParser jParser = new JSONParser();
    ArrayList<Karyawan> daftar_karyawan = new ArrayList<Karyawan>();
    JSONArray daftarKar = null;
    String url_read_karyawan = "http://192.168.56.1/karyawan/read_karyawan.php";

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_KARYAWAN = "karyawan";
    public static final String TAG_ID_KAR = "id_kar";
    public static final String TAG_NAMA_KAR = "nama_kar";
    public static final String TAG_ALAMAT_KAR = "alamat_kar";
    public static final String TAG_EMAIL_KAR = "email_kar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(i);
            //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //            .setAction("Action", null).show();
            }
        });

        list = (ListView) findViewById(R.id.list_karyawan);

        ReadKarTask m = (ReadKarTask) new ReadKarTask().execute();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int urutan, long id) {
                String idkar = ((TextView) view.findViewById(R.id.id_karyawan)).getText().toString();
                String namakar = ((TextView) view.findViewById(R.id.nama_karyawan)).getText().toString();
                String alamatkar = ((TextView) view.findViewById(R.id.alamat_karyawan)).getText().toString();
                String emailkar = ((TextView) view.findViewById(R.id.email_karyawan)).getText().toString();

                Intent i = null;
                i = new Intent(MainActivity.this, UpdateDeleteActivity.class);
                Bundle b = new Bundle();
                b.putString("id_kar", idkar);
                b.putString("nama_kar", namakar);
                b.putString("alamat_kar", alamatkar);
                b.putString("email_kar", emailkar);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ReadKarTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait ..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            String returnResult = getKarList();
            return returnResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if (result.equalsIgnoreCase("Exception Caught")) {
                Toast.makeText(MainActivity.this, "Unable to connect to server, please check your internet connection!", Toast.LENGTH_LONG).show();
            } else if (result.equalsIgnoreCase("No Results")) {
                Toast.makeText(MainActivity.this, "Data Empty", Toast.LENGTH_LONG).show();
            } else {
                list.setAdapter(new KaryawanAdapter(MainActivity.this, daftar_karyawan));
            }
        }

        public String getKarList() {
            Karyawan tempKar = new Karyawan();
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            try {
                JSONObject json = jParser.makeHttpRequest(url_read_karyawan, "POST", parameter);

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    daftarKar = json.getJSONArray(TAG_KARYAWAN);

                    for (int i = 0; i < daftarKar.length(); i++) {
                        JSONObject c = daftarKar.getJSONObject(i);
                        tempKar = new Karyawan();
                        tempKar.setId_kar(c.getString(TAG_ID_KAR));
                        tempKar.setNama_kar(c.getString(TAG_NAMA_KAR));
                        tempKar.setAlamat_kar(c.getString(TAG_ALAMAT_KAR));
                        tempKar.setEmail_kar(c.getString(TAG_EMAIL_KAR));
                        daftar_karyawan.add(tempKar);
                    }
                    return "OK";
                } else {
                    return "No Results";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Caught";
            }
        }
    }
}
