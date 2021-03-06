package com.mascitra.android.pengaduanlmj.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mascitra.android.pengaduanlmj.R;

import com.mascitra.android.pengaduanlmj.activity.DetailPengaduanActivity;
import com.mascitra.android.pengaduanlmj.adapter.DataDashboardAdapter;
import com.mascitra.android.pengaduanlmj._sliders.SliderPagerAdapter;
import com.mascitra.android.pengaduanlmj.Data.DataPengaduan;
import com.mascitra.android.pengaduanlmj.config.RequestHandler;
import com.mascitra.android.pengaduanlmj.config.SettingDatabase;
import com.mascitra.android.pengaduanlmj._sliders.FragmentSlider;
import com.mascitra.android.pengaduanlmj._sliders.SliderIndicator;
import com.mascitra.android.pengaduanlmj._sliders.SliderView;


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

/**
 * Created by SONY on 28/08/2017.
 */

public class FragmentDashboard extends Fragment {


    private ListView listView;
    private String JSON_STRING;

    List<DataPengaduan> data_list;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private DataDashboardAdapter adapterCard;

    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;

    private SliderView sliderView;
    private LinearLayout mLinearLayout;
    ImageView imgaduan;
    TextView urlimages;

    public FragmentDashboard(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        listView = view.findViewById(R.id.list);
        imgaduan = view.findViewById(R.id.imageList);
        urlimages = view.findViewById(R.id.url);

        getJSON();

        Refreshlist();

        recyclerView = view.findViewById(R.id.rc_data);
        data_list = new ArrayList<>();
        load_data(0);

        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapterCard = new DataDashboardAdapter(getActivity(),data_list);
        recyclerView.setAdapter(adapterCard);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size()-1){
                    load_data(data_list.get(data_list.size()-1).getId());
                }

            }
        });

        sliderView = view.findViewById(R.id.sliderView);
        mLinearLayout = view.findViewById(R.id.pagesContainer);

        setupSlider();


        return view;

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


    private void setupSlider() {
        sliderView.setDurationScroll(800);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentSlider.newInstance("http://mars7sq.student.umm.ac.id/wp-content/uploads/sites/20442/2016/06/maxresdefault.jpg"));
        fragments.add(FragmentSlider.newInstance("http://anekatempatwisata.com/wp-content/uploads/2014/03/Pantai-Balekambang.jpg"));
        fragments.add(FragmentSlider.newInstance("http://www.wisatajatim.info/wp-content/uploads/2015/11/puncak-b29-lumajang.jpg"));
        fragments.add(FragmentSlider.newInstance("http://bisniswisata.co.id/wp-content/uploads/2015/07/B29-lumajang.jpg"));
        mAdapter = new SliderPagerAdapter(getFragmentManager(), fragments);
        sliderView.setAdapter(mAdapter);
        mIndicator = new SliderIndicator(getActivity(), mLinearLayout, sliderView, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();

    }

    public void Refreshlist(){

        try {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> arg0, View view, final int pos, long l) {

                    Intent intent = new Intent(getActivity(), DetailPengaduanActivity.class);
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
                getActivity(), list, R.layout.list_item,
                new String[]{SettingDatabase.TAG_KTP, SettingDatabase.TAG_NAMA, SettingDatabase.TAG_WAKTU, SettingDatabase.TAG_ISI},
                new int[]{R.id.ktp_id, R.id.name_ktp, R.id.jam, R.id.komentar});


        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Mengambil Data","Mohon Tunggu...",false,false);
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
