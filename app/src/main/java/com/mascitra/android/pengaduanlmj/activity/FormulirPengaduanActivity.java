package com.mascitra.android.pengaduanlmj.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mascitra.android.pengaduanlmj.config.*;
import com.mascitra.android.pengaduanlmj.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by SONY on 25/08/2017.
 */

public class FormulirPengaduanActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spin_skpd,spin_kec,spin_kel;

    String[] skpd = {"Pilih SKPD","Dinas Komunikasi dan Informatika", "Dinas Kesehatan", "Dinas Sosial", "Dinas Pendidikan",
            "Dinas Kependudukan dan Pencatatan Sipil", "Dinas Pekerjaan Umum dan Tata Ruang", "Dinas Perikanan", "Perusahaan Daerah Air Minum",
            "Sekretariat DPRD", "Bagian Administrasi Pemerintahan"};
    String [] kecamatan = {"Pilih Kecamatan" , "Kecamatan Sukodono", "Kecamatan Yosowilangun", "Kecamatan Pasirian", "Kecamatan Kunir"};
    String [] kelurahan = {"Pilih Kelurahan","Rogotrunan", "Ditrotunan"};

     EditText namap,ktp,rt,rw,alamats,emaile,isi;
     Button btnsimpan;

    private ImageButton btnGallery;
    private ImageButton btnCamera;

    private LayoutInflater menuInflater;
    private static final int PICT_IMAGE = 100;
    Uri imageUri;

    ImageView mimageView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_formulir_pengaduan);

        spin_skpd = (Spinner) findViewById(R.id.skpd);
        spin_kec = (Spinner) findViewById(R.id.kecamatan);
        spin_kel = (Spinner) findViewById(R.id.kelurahan);

//        btnGalery = view.findViewById(R.id.btnGalery);
        btnCamera =  (ImageButton) findViewById(R.id.btnCamera);
        btnGallery = (ImageButton) findViewById(R.id.btnGalery);

        ArrayKecamatan();
        ArrayKelurahan();
        Arrayrskpd();

        namap = (EditText) findViewById(R.id.nama);
        ktp = (EditText)findViewById(R.id.no_ktp);
        rt = (EditText)findViewById(R.id.rt);
        rw =(EditText)findViewById(R.id.rw);
        alamats = (EditText)findViewById(R.id.alamat);
        emaile = (EditText)findViewById(R.id.email);
        isi = (EditText)findViewById(R.id.isi_pengaduan);

        btnCamera.setOnClickListener(this);
        btnGallery.setOnClickListener(this);

        btnsimpan = (Button) findViewById(R.id.btnSimpan);
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPengaduan();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Data Pengaduan");
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

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.fragment_formulir_pengaduan, container, false);
        spin_skpd = (Spinner) findViewById(R.id.skpd);
        spin_kec = (Spinner) findViewById(R.id.kecamatan);
        spin_kel = (Spinner) findViewById(R.id.kelurahan);

//        btnGalery = view.findViewById(R.id.btnGalery);
        btnCamera =  (ImageButton) findViewById(R.id.btnCamera);
        btnGallery = (ImageButton) findViewById(R.id.btnGalery);

        ArrayKecamatan();
        ArrayKelurahan();
        Arrayrskpd();

        namap = (EditText) findViewById(R.id.nama);
        ktp = (EditText)findViewById(R.id.no_ktp);
        rt = (EditText)findViewById(R.id.rt);
        rw =(EditText)findViewById(R.id.rw);
        alamats = (EditText)findViewById(R.id.alamat);
        emaile = (EditText)findViewById(R.id.email);
        isi = (EditText)findViewById(R.id.isi_pengaduan);

        btnCamera.setOnClickListener(this);
        btnGallery.setOnClickListener(this);

        btnsimpan = (Button) findViewById(R.id.btnSimpan);
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPengaduan();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Data Pengaduan");
//        stubmenu = view.findViewById(R.id.stub_menu);
//        stubmenu.inflate();


        return view;
    }

    public void takeImageFromCamera(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 0);
    }

    public void takeImageFromGalery(View view){
        Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galery, PICT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (btnCamera.isClickable()){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            mimageView.setImageBitmap(bitmap);
        }

        if (btnGallery.isClickable()){
            imageUri = data.getData();
            mimageView.setImageURI(imageUri);
        }

    }

    private void addPengaduan(){

        final String vnama = namap.getText().toString().trim();
        final String vno_ktp = ktp.getText().toString().trim();
        final String vkecamatan = spin_kec.getSelectedItem().toString();
        final String vkelurahan = spin_kel.getSelectedItem().toString();
        final String vrt = rt.getText().toString().trim();
        final String vrw =rw.getText().toString().trim();
        final String valamat = alamats.getText().toString().trim();
        final String vemail = emaile.getText().toString().trim();
        final String vpeng_kpd = spin_skpd.getSelectedItem().toString().trim();
        final String vpeng_isi = isi.getText().toString().trim();
//        final String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy, HH:mm");
        final String formattedDate = df.format(c.getTime());

        class addPengaduan extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FormulirPengaduanActivity.this, "Menambahkan...","Tunggu...", false, false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(FormulirPengaduanActivity.this, s,Toast.LENGTH_LONG).show();
                cleartext();
            }


            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
        //        params.put(SettingDatabase.KEY_EMP_ID, id);
                params.put(SettingDatabase.KEY_EMP_NAMA, vnama);
                params.put(SettingDatabase.KEY_KTP, vno_ktp);
                params.put(SettingDatabase.KEY_KEC, vkecamatan);
                params.put(SettingDatabase.KEY_KEL, vkelurahan);
                params.put(SettingDatabase.KEY_RT, vrt);
                params.put(SettingDatabase.KEY_RW, vrw);
                params.put(SettingDatabase.KEY_ALAMAT, valamat);
                params.put(SettingDatabase.KEY_EMAIL, vemail);
                params.put(SettingDatabase.KEY_KPD, vpeng_kpd);
                params.put(SettingDatabase.KEY_ISI, vpeng_isi);
                params.put(SettingDatabase.KEY_WAKTU, formattedDate);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(SettingDatabase.URL_ADD, params);
                return res;
            }
        }

        addPengaduan addPen = new addPengaduan();
        addPen.execute();
    }

    void cleartext(){
        namap.getText().clear();
        namap.requestFocus();
        ktp.getText().clear();
        spin_kec.getSelectedItem().equals("Pilih Kecamatan");
        spin_kel.getSelectedItem().equals("Pilih Kelurahan");
        rt.getText().clear();
        rw.getText().clear();
        alamats.getText().clear();
        emaile.getText().clear();
        spin_skpd.getSelectedItem().equals("Pilih SKPD");
        isi.getText().clear();
    }

    private void Arrayrskpd(){
    ArrayAdapter<String> aa = new ArrayAdapter<String>(FormulirPengaduanActivity.this, android.R.layout.simple_spinner_dropdown_item, skpd);
    spin_skpd.setAdapter(aa);
    spin_skpd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            Toast.makeText(null, "Silahkan memilih salah satu", Toast.LENGTH_LONG).show();
        }
    });
}

    private void ArrayKelurahan(){
            ArrayAdapter<String> aa = new ArrayAdapter<String>(FormulirPengaduanActivity.this, android.R.layout.simple_spinner_dropdown_item, kelurahan);
            spin_kel.setAdapter(aa);
            spin_kel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    Toast.makeText(null, "Silahkan memilih salah satu", Toast.LENGTH_LONG).show();
                }
            });
        }

    private void ArrayKecamatan(){
            ArrayAdapter<String> aa = new ArrayAdapter<String>(FormulirPengaduanActivity.this, android.R.layout.simple_spinner_dropdown_item, kecamatan);
            spin_kec.setAdapter(aa);
            spin_kec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    Toast.makeText(null, "Silahkan memilih salah satu", Toast.LENGTH_LONG).show();
                }
            });
        }


    @Override
    public void onClick(View view) {
        if (view == btnCamera){
            takeImageFromCamera(view);
        } else if (view == btnGallery){
            takeImageFromGalery(view);
        }
    }
}
