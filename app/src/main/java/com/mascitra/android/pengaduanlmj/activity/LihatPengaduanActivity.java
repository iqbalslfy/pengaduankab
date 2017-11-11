package com.mascitra.android.pengaduanlmj.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mascitra.android.pengaduanlmj.Data.DataPengaduan;
import com.mascitra.android.pengaduanlmj.R;
import com.mascitra.android.pengaduanlmj.adapter.DataDashboardAdapter;
import com.mascitra.android.pengaduanlmj.config.RequestHandler;
import com.mascitra.android.pengaduanlmj.config.SettingDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LihatPengaduanActivity extends AppCompatActivity {

    List<DataPengaduan> data_list;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private DataDashboardAdapter adapterCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_pengaduan);
        recyclerView = (RecyclerView) findViewById(R.id.rc_list_pengaduan);
        data_list = new ArrayList<>();
        load_data(0);

        gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapterCard = new DataDashboardAdapter(this,data_list);
        recyclerView.setAdapter(adapterCard);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size()-1){
                    load_data(data_list.get(data_list.size()-1).getId());
                }

            }
        });


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

    private void load_data(final int id) {
        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://demo.pertaminapontianak.com/view2.php?id="+integers[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        DataPengaduan data = new DataPengaduan(
                                object.getInt("id"),
                                object.getString("image"),
                                object.getString("no_ktp"),
                                object.getString("nama"),
                                object.getString("waktu"),
                                object.getString("peng_isi")

                        );

                        data_list.add(data);
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapterCard.notifyDataSetChanged();
            }
        };

        task.execute(id);
    }
}
