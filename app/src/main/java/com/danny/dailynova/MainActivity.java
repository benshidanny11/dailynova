package com.danny.dailynova;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.danny.dailynova.fragments.FragmentBusiness;
import com.danny.dailynova.fragments.FragmentLifeStyle;
import com.danny.dailynova.fragments.FragmentLyrics;
import com.danny.dailynova.fragments.FragmentTrending;
import com.danny.dailynova.fragments.FragmentVideo;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    ImageView imgSerch,imgUserProfile;
    TextView txtNotification;
    TextView txtUserName;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment=null;
            switch (item.getItemId()) {
                case R.id.nav_trending:
                   selectedFragment=new FragmentTrending();
                   break;
                case R.id.nav_lyrics:
                    selectedFragment=new FragmentLyrics();
                    break;
                case R.id.nav_videos:
                    selectedFragment=new FragmentVideo();
                    break;
                case R.id.nav_life_style:
                    selectedFragment=new FragmentLifeStyle();
                    break;
                case R.id.nav_busines:
                    selectedFragment=new FragmentBusiness();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = (BottomNavigationView)  findViewById(R.id.nav_view);

        imgSerch=(ImageView)findViewById(R.id.img_action_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_drawer);
        txtNotification=(TextView)findViewById(R.id.txt_notification_badge_in_app_bar);
        navigationView.setNavigationItemSelectedListener(this);
        navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentTrending()).commit();
        imgSerch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getVisibleFragment() instanceof FragmentTrending){
                    Intent intent=new Intent(MainActivity.this,SearchResultActivity.class);
                    intent.putExtra("fragment","trending");
                    startActivity(intent);
                   // Toast.makeText(MainActivity.this, "In trending fragment", Toast.LENGTH_SHORT).show();
                }
                else if(getVisibleFragment() instanceof FragmentVideo){
                    Intent intent=new Intent(MainActivity.this,SearchResultActivity.class);
                    intent.putExtra("fragment","video");
                    startActivity(intent);
                   // Toast.makeText(MainActivity.this, "In video fragment", Toast.LENGTH_SHORT).show();
                }
                else if(getVisibleFragment() instanceof FragmentLyrics){
                    Intent intent=new Intent(MainActivity.this,SearchResultActivity.class);
                    intent.putExtra("fragment","lyrics");
                    startActivity(intent);
                    //Toast.makeText(MainActivity.this, "In lyrics fragment", Toast.LENGTH_SHORT).show();
                }
                else if(getVisibleFragment() instanceof FragmentLifeStyle){
                    Intent intent=new Intent(MainActivity.this,SearchResultActivity.class);
                    intent.putExtra("fragment","lifestyle");
                    startActivity(intent);
                    //Toast.makeText(MainActivity.this, "In lifestyle fragment", Toast.LENGTH_SHORT).show();
                }
                else if(getVisibleFragment() instanceof FragmentBusiness){
                    Intent intent=new Intent(MainActivity.this,SearchResultActivity.class);
                    intent.putExtra("fragment","business");
                    startActivity(intent);
                   // Toast.makeText(MainActivity.this, "In updates fragment", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET},
                1);
        final View header=navigationView.getHeaderView(0);
        imgUserProfile=(ImageView)header.findViewById(R.id.img_user_profile_in_main);
        txtUserName=(TextView)header.findViewById(R.id.txt_user_name_in_main_activity_1);

       if (getIntent().getExtras()!=null){

          txtUserName.setText(getIntent().getExtras().getString("displayName"));
           Glide.with(this).load(getIntent().getExtras().getString("imgUrl")).into(imgUserProfile);

       }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_latest_news) {
            Toast.makeText(this, "Not yet added", Toast.LENGTH_SHORT).show();

        }
        else if (id == R.id.nav_tech_news) {
            Toast.makeText(this, "Not yet added", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_share_app) {
            Toast.makeText(this, "Not yet added", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_other_news) {
            Toast.makeText(this, "Not yet added", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_create_account) {
            Toast.makeText(this, "Not yet added", Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
