package com.mascitra.android.pengaduanlmj.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mascitra.android.pengaduanlmj.R;
import com.mascitra.android.pengaduanlmj.config.RequestHandler;
import com.mascitra.android.pengaduanlmj.config.SettingDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LihatPengaduanActivity extends AppCompatActivity {

    private ListView listView;
    private String JSON_STRING;
    TextView uRL;
    ImageView imgaduan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_pengaduan);
        uRL = (TextView) findViewById(R.id.url);
        listView = (ListView) findViewById(R.id.list);

        getJSON();

        imgaduan = (ImageView) findViewById(R.id.imageList);
        Refreshlist();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pengaduan Masyarakat");
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

    public void Refreshlist(){

        try {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> arg0, View view, final int pos, long l) {

                    Intent intent = new Intent(getApplicationContext(), DetailPengaduanActivity.class);
                    AdapterView<?> adapterView = null;
                    HashMap<String,String> map =(HashMap)arg0.getItemAtPosition(pos);
                    String empId = map.get(SettingDatabase.TAG_KTP).toString();
                    intent.putExtra(SettingDatabase.TAG_KTP,empId);
                    startActivity(intent);

                }
            });
        }catch (Exception e){
            e.getMessage();
        }
    }

    public void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(SettingDatabase.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id_ktp = jo.getString(SettingDatabase.TAG_KTP);
                String name = jo.getString(SettingDatabase.TAG_NAMA);
                String waktu = jo.getString(SettingDatabase.TAG_WAKTU);
                String isi = jo.getString(SettingDatabase.TAG_ISI);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(SettingDatabase.TAG_KTP,id_ktp);
                employees.put(SettingDatabase.TAG_NAMA,name);
                employees.put(SettingDatabase.TAG_WAKTU, waktu);
                employees.put(SettingDatabase.TAG_ISI, isi);

                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                this, list, R.layout.list_item,
                new String[]{SettingDatabase.TAG_KTP, SettingDatabase.TAG_NAMA, SettingDatabase.TAG_WAKTU, SettingDatabase.TAG_ISI},
                new int[]{R.id.ktp_id, R.id.name_ktp, R.id.jam, R.id.komentar});


        listView.setAdapter(adapter);
    }

    public void getJSON(){

        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatPengaduanActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(SettingDatabase.URL_GET_ALL);
                return s;
            }
        }

        GetJSON gj = new GetJSON();
        gj.execute();

    }
}
