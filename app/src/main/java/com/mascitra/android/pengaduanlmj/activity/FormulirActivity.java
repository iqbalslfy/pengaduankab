package com.mascitra.android.pengaduanlmj.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mascitra.android.pengaduanlmj.R;
import com.mascitra.android.pengaduanlmj.config.RequestHandler;
import com.mascitra.android.pengaduanlmj.config.SettingDatabase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class FormulirActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spin_skpd,spin_kec,spin_kel;

    String[] skpd = {"Pilih SKPD","Dinas Komunikasi dan Informatika", "Dinas Kesehatan", "Dinas Sosial", "Dinas Pendidikan",
            "Dinas Kependudukan dan Pencatatan Sipil", "Dinas Pekerjaan Umum dan Tata Ruang", "Dinas Perikanan", "Perusahaan Daerah Air Minum",
            "Sekretariat DPRD", "Bagian Administrasi Pemerintahan"};
    String [] kecamatan = {"Pilih Kecamatan" , "Kecamatan Sukodono", "Kecamatan Yosowilangun", "Kecamatan Pasirian", "Kecamatan Kunir"};
    String [] kelurahan = {"Pilih Kelurahan","Rogotrunan", "Ditrotunan"};

    ImageButton btnCamera, btnGalery;
    private static final int PICT_IMAGE = 100;

    int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    ImageView imageView;
    EditText namap,ktp,rt,rw,alamats,emaile,isi;
    Button btnSimpan;
    Bitmap bitmap, decode;
    int success;
    int bitmap_size = 60;

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_formulir_pengaduan);
        imageView = (ImageView)findViewById(R.id.imagePengaduan);

        btnGalery = (ImageButton)findViewById(R.id.btnGalery);
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);

        namap = (EditText) findViewById(R.id.nama);
        ktp = (EditText)findViewById(R.id.no_ktp);
        rt = (EditText)findViewById(R.id.rt);
        rw =(EditText)findViewById(R.id.rw);
        alamats = (EditText)findViewById(R.id.alamat);
        emaile = (EditText)findViewById(R.id.email);
        isi = (EditText)findViewById(R.id.isi_pengaduan);

        btnGalery.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);

        spin_skpd = (Spinner) findViewById(R.id.skpd);
        spin_kec = (Spinner) findViewById(R.id.kecamatan);
        spin_kel = (Spinner) findViewById(R.id.kelurahan);

        ArrayKecamatan();
        ArrayKelurahan();
        Arrayrskpd();

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

    private void ArrayKecamatan(){
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, kecamatan);
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

    private void ArrayKelurahan(){
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, kelurahan);
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

    private void Arrayrskpd(){
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, skpd);
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

    public void takeImageFromCamera(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 0);
    }

    public void takeImageFromGalery(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
        imageView.setImageResource(0);
    }

    public void addPengaduan(View view){

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

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy, HH:mm");
        final String formattedDate = df.format(c.getTime());

        class addPengaduan extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FormulirActivity.this, "Menambahkan...","Tunggu...", false, false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(FormulirActivity.this, s,Toast.LENGTH_LONG).show();

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
                params.put(SettingDatabase.KEY_FOTO, getStringImage(decode) );

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(SettingDatabase.URL_ADD, params);
                return res;
            }
        }

        addPengaduan addPen = new addPengaduan();
        addPen.execute();
    }




    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decode = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        imageView.setImageBitmap(decode);
    }

    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


                if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri filePath = data.getData();
                    try {
                        //mengambil fambar dari Gallery
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                        setToImageView(getResizedBitmap(bitmap, 1024));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

    }

    @Override
    public void onClick(View view) {
        if (view == btnCamera){
            takeImageFromCamera(view);

        } else if (view == btnGalery){
            takeImageFromGalery(view);
        } else if (view == btnSimpan){
            addPengaduan(view);
        }
    }
}
