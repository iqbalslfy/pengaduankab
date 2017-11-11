package com.mascitra.android.pengaduanlmj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mascitra.android.pengaduanlmj.R;
import com.mascitra.android.pengaduanlmj.fragment.FragmentDashboard;
import com.mascitra.android.pengaduanlmj.other.CircleTransform;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navheader;
    private ImageView imNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    //url to load navigation header background image and profil
    private static final String urlNavheaderBg = "https://4.bp.blogspot.com/-OveiGoo6G7k/V2DPt7_GA3I/AAAAAAAAEgo/hVPzOnk8d3cbOMTTrSuUjW51fFYP10WJQCLcB/s640/alun%2Balun%2Bkeren%2Blumajang.jpg";
    private static final String urlProfilImage  = "https://upload.wikimedia.org/wikipedia/commons/4/45/Logo_kabupaten_lumajang.jpg";

//    index to identify current image nav menu
    public static int navItemIndex = 0;

//    tag used to attach the fragment
    private static final String TAG_DASHBOARD = "dashboard";
    public static String CURRENT_TAG = TAG_DASHBOARD;

//    Toolbar titles respect to selected to selected nav menu item
    private String[] activityTitles;
    private String JSON_STRING;


    private boolean shouldLoadHomeFragObBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

//        Navigation view header
        navheader = navigationView.getHeaderView(0);
        txtName =  navheader.findViewById(R.id.name);
        txtWebsite = navheader.findViewById(R.id.website);
        imNavHeaderBg = navheader.findViewById(R.id.img_header_bg);
        imgProfile = navheader.findViewById(R.id.img_profile);


//        Load toolbar titles from string string resource
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(this);

        LoadNavHeader();



        setUpNavigationView();

       if (savedInstanceState == null){
           navItemIndex = 0;
           CURRENT_TAG = TAG_DASHBOARD;
           loadHomeFragment();
       }


    }


    private void LoadNavHeader() {
//        name,website
        txtName.setText("Layanan Pengaduan Masyarakat");
        txtWebsite.setText("APLIKASI PENGADUAN ");

        Glide.with(this).load(urlNavheaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imNavHeaderBg);

        Glide.with(this).load(urlProfilImage)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        //       showing dot next to notifications label
                 navigationView.getMenu().getItem(1).setActionView(R.layout.menu_dot);

    }

    private void loadHomeFragment(){
        selectNavMenu();
//        set toolbar title
        setToolbarTitle();

//        if user select current navigation menu again, don't again, do anything
//        just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null){
            drawer.closeDrawers();

            toggleFab();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
//                update main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

//    if mPendingRunnable is not null. then add to the message queue
        if (mPendingRunnable != null){
            mHandler.post(mPendingRunnable);
        }

//        show or hide the fab button
        toggleFab();

//        closing drawer on item
        drawer.closeDrawers();

//        refresh toolbar menu
        invalidateOptionsMenu();
    }


    public  Fragment getHomeFragment(){

        switch (navItemIndex){
            case 0:
                FragmentDashboard fragmentDashboard = new FragmentDashboard();
                return fragmentDashboard;
            default:
                return new FragmentDashboard();
        }
    }

    private void toggleFab() {
        if (navItemIndex == 0){
            fab.show();
        }else{
            fab.hide();
        }

    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_DASHBOARD;
                        break;
                    case R.id.nav_show_complaint:
                        navItemIndex = 1;
                        startActivity(new Intent(DashboardActivity.this, LihatPengaduanActivity.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_form:
                        navItemIndex = 2;

                        startActivity(new Intent(DashboardActivity.this, FormulirActivity.class));
                        drawer.closeDrawers();
                        return true;

                    case R.id.nav_help_guide:
                        navItemIndex = 3;
                        startActivity(new Intent(DashboardActivity.this, PanduanActivity.class));
                        break;
                    case R.id.nav_about_us:
                        navItemIndex = 4;

                        startActivity(new Intent(DashboardActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;

                    default:
                        navItemIndex = 0;
                }
                //Checking if the item is in checked state or not, if not make it in checked state
                if (item.isChecked()){
                    item.setChecked(false);
                }else{
                    item.setChecked(true);
                }
                item.setChecked(true);
                loadHomeFragment();
                return true;

            }
        });

        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawer.setDrawerListener(abdt);
        abdt.syncState();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawers();
            return;
        }

        if (shouldLoadHomeFragObBackPress){
            if (navItemIndex != 0){
                navItemIndex = 0;
                CURRENT_TAG = TAG_DASHBOARD;
                loadHomeFragment();

                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        load just in fragment home

        if (navItemIndex == 2){
            getMenuInflater().inflate(R.menu.formulir, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout){
            Toast.makeText(getApplicationContext(), "Logout user", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_cancel){

         onBackPressed();

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        if (view == fab){
            startActivity(new Intent(DashboardActivity.this, FormulirActivity.class));
            drawer.closeDrawers();
        }
    }
}
