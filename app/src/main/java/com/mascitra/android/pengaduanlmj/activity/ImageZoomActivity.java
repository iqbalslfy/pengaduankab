package com.mascitra.android.pengaduanlmj.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mascitra.android.pengaduanlmj.R;
import com.mascitra.android.pengaduanlmj.config.SettingDatabase;

public class ImageZoomActivity extends AppCompatActivity {

    private String ktp_id;
    TextView tvlink;
    String URL;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pengaduan Lumajang");

        tvlink = (TextView) findViewById(R.id.tvlink);
        imageView = (ImageView) findViewById(R.id.imageDetailZoom);

        Bundle b = getIntent().getExtras();
        tvlink.setText(b.getCharSequence("url"));

        URL = String.valueOf(tvlink.getText());
        Glide.with(this).load(URL)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
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
