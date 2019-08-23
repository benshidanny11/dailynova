package com.example.dailynova;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dailynova.fragments.FragmentLifeStyle;
import com.example.dailynova.fragments.FragmentLyrics;
import com.example.dailynova.fragments.FragmentTrending;
import com.example.dailynova.fragments.FragmentUpddates;
import com.example.dailynova.fragments.FragmentVideo;


import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    ImageView imgSerch;

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
                case R.id.nav_news:
                    selectedFragment=new FragmentUpddates();
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
                else if(getVisibleFragment() instanceof FragmentUpddates){
                    Intent intent=new Intent(MainActivity.this,SearchResultActivity.class);
                    intent.putExtra("fragment","updates");
                    startActivity(intent);
                   // Toast.makeText(MainActivity.this, "In updates fragment", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET},
                1);
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
