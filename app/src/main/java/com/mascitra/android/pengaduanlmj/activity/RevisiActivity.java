package com.mascitra.android.pengaduanlmj.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mascitra.android.pengaduanlmj.R;
import com.mascitra.android.pengaduanlmj.adapter.ViewPagerAdapter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by SONY on 04/11/2017.
 */

public class RevisiActivity extends AppCompatActivity{
    ViewPager viewPager;
    LinearLayout SliderDots;
    private int dotcounts;
    private ImageView[] dots;
    private Timer timer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_refisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Data Pengaduan");
        viewPager = (ViewPager) findViewById(R.id.viewPagerHome);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        SliderDots = (LinearLayout) findViewById(R.id.Dots);
        dotcounts = viewPagerAdapter.getCount();

        dots = new ImageView[dotcounts];

        for (int i = 0; i < dotcounts; i++){
            dots[i] = new ImageView(RevisiActivity.this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(RevisiActivity.this, R.drawable.indicator_enable));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8 ,0);
            SliderDots.addView(dots[i], params);
        }



        dots[0].setImageDrawable(ContextCompat.getDrawable(RevisiActivity.this, R.drawable.indicator_unable));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i=0; i<dotcounts; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(RevisiActivity.this, R.drawable.indicator_unable));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(RevisiActivity.this, R.drawable.indicator_enable));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTasks(), 1000, 2000);
    }

    public class TimerTasks extends TimerTask {

        @Override
        public void run() {
            RevisiActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }

                }
            });


        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        timer.cancel();
        timer.purge();
    }
}
