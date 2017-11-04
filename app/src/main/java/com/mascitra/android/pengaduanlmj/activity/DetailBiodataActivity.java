package com.mascitra.android.pengaduanlmj.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mascitra.android.pengaduanlmj.R;
import com.mascitra.android.pengaduanlmj.config.RequestHandler;

import com.mascitra.android.pengaduanlmj.config.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailBiodataActivity extends AppCompatActivity {

    private EditText nama,ktp,kec,kel,rt,rw,alamat,email,skpd,keluhan, url;
    private String ktp_id;
    private ImageView imgaduan;
    private String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_biodata);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Pengaduan");
        Intent intent = getIntent();

        ktp_id = intent.getStringExtra(SettingDatabase.KEY_KTP);

        nama = (EditText)findViewById(R.id.dnama);
        ktp = (EditText)findViewById(R.id.did_ktp);
        kec = (EditText)findViewById(R.id.dkec);
        kel = (EditText)findViewById(R.id.dkel);
        rt = (EditText)findViewById(R.id.drt);
        rw = (EditText)findViewById(R.id.drw);
        alamat = (EditText)findViewById(R.id.dalamat);
        email = (EditText) findViewById(R.id.demail);

        Bundle b = getIntent().getExtras();
        ktp.setText(b.getCharSequence("ktp"));

        getData();
    }

    private void getData(){
        class getDetail extends AsyncTask<Void, Void,String>{

            ProgressDialog loding;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loding = ProgressDialog.show(DetailBiodataActivity.this, "Mencocokkan data...","Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loding.dismiss();
                LihatData(s);
            }


            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(SettingDatabase.URL_GET_DETAIL, ktp_id);
                return s;
            }
        }
        getDetail ge = new getDetail();
        ge.execute();
    }

    private void LihatData(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(SettingDatabase.TAG_JSON_ARRAY);
            JSONObject obj = result.getJSONObject(0);
            String h1 = obj.getString(SettingDatabase.TAG_NAMA);
            String h2 = obj.getString(SettingDatabase.TAG_KTP);
            String h3 = obj.getString(SettingDatabase.TAG_KEC);
            String h4 = obj.getString(SettingDatabase.TAG_KEL);
            String h5 = obj.getString(SettingDatabase.TAG_RT);
            String h6 = obj.getString(SettingDatabase.TAG_RW);
            String h7 = obj.getString(SettingDatabase.TAG_ALAMAT);
            String h8 = obj.getString(SettingDatabase.TAG_EMAIL);

            nama.setText(h1);
            ktp.setText(h2);
            kec.setText(h3);
            kel.setText(h4);
            rt.setText(h5);
            rw.setText(h6);
            alamat.setText(h7);
            email.setText(h8);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
