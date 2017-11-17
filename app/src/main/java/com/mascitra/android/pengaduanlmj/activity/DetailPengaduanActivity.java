package com.mascitra.android.pengaduanlmj.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mascitra.android.pengaduanlmj.R;
import com.mascitra.android.pengaduanlmj.config.RequestHandler;
import com.mascitra.android.pengaduanlmj.config.SettingDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailPengaduanActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView people, ktpd, kepada,txtwaktu, isipengaduan, UrlImage, tanggapan, txtWaktuTanggapan;
    private String ktp_ids;
    private ImageView imgaduan;
    private  String URL;
    private String tanggapantext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengaduan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Pengaduan");


        Intent intent = getIntent();
        ktp_ids = intent.getStringExtra(SettingDatabase.TAG_KTP);
//        tanggapantext = intent.getStringExtra(SettingDatabase.TAG_ISI);

        people = (TextView) findViewById(R.id.textpeople);
        ktpd = (TextView) findViewById(R.id.no_ktpd);
        kepada = (TextView) findViewById(R.id.textkepada);
        txtwaktu = (TextView) findViewById(R.id.textwaktu);
        isipengaduan = (TextView) findViewById(R.id.textpengaduan);
        UrlImage = (TextView) findViewById(R.id.urlimages);
        imgaduan = (ImageView) findViewById(R.id.imageDetail);
        tanggapan = (TextView)findViewById(R.id.textTanggapan);
        txtWaktuTanggapan = (TextView) findViewById(R.id.txttgltanggapan);

        String v = "Belum ada Tanggapan dari :";

//        if (SettingDatabase.KEY_ISI.isEmpty()){
//            tanggapan.setText(v+kepada.getText());
//        }

        people.setOnClickListener(this);
        imgaduan.setOnClickListener(this);

        getData();

//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if(getSupportActionBar() != null){

//        }

    }

    private void getData(){
        class getDetail extends AsyncTask<Void, Void,String> {

            ProgressDialog loding;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loding = ProgressDialog.show(DetailPengaduanActivity.this, "Mencocokkan data...","Tunggu...", false, false);
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
                String s = rh.sendGetRequestParam(SettingDatabase.URL_GET_PERDA, ktp_ids);
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
            String h2 = obj.getString(SettingDatabase.TAG_KPD);
            String h3 = obj.getString(SettingDatabase.TAG_WAKTU);
            String h4 = obj.getString(SettingDatabase.TAG_FOTO);
            String h5 = obj.getString(SettingDatabase.TAG_ISI);
            String h6 = obj.getString(SettingDatabase.TAG_KTP);
            String h7 = obj.getString(SettingDatabase.TAG_TGL_TGGP);
            String h8 = obj.getString(SettingDatabase.TAG_TANGGAPAN);

            people.setText(h1);
            kepada.setText(h2);
            txtwaktu.setText(h3);
            UrlImage.setText(h4);
            isipengaduan.setText(h5);
            ktpd.setText(h6);
            txtWaktuTanggapan.setText(h7);

            if (h8.equals("")){
                tanggapan.setText("asdfasf");
            }else {
                tanggapan.setText(h8);
            }

            URL = String.valueOf(UrlImage.getText());
            Glide.with(this).load(URL)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgaduan);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == people){
            Intent intent = new Intent(getApplicationContext(), DetailBiodataActivity.class);
            Bundle b = new Bundle();
            b.putString("ktp", ktpd.getText().toString());
            intent.putExtras(b);
            startActivity(intent);
        }

        if (view == imgaduan){
            Intent intent = new Intent(getApplicationContext(), ImageZoomActivity.class);
            Bundle b = new Bundle();
            b.putString("url", UrlImage.getText().toString());
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}
