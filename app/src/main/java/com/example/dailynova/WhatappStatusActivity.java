package com.example.dailynova;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.dailynova.adapters.FilesAdapter;

import java.io.File;
import java.util.ArrayList;

public class WhatappStatusActivity extends AppCompatActivity {

    RecyclerView recStatusList;
    FilesAdapter filesAdapter;
    File directory;
    boolean isImage=false;
    ArrayList<File> statusesList;
    File listFile[] ;
    ImageView imgSelectFile,imgActionBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatapp_status);
        recStatusList=(RecyclerView)findViewById(R.id.rec_statuses_saver);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_status_downloader);
        setSupportActionBar(toolbar);

        imgSelectFile=(ImageView)findViewById(R.id.img_select_file_in_whatapp_downloader);
        imgActionBack=(ImageView)findViewById(R.id.img_action_back_in_whatapp_status);
        imgSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recStatusList!=null){

                }
            }
        });
        imgActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WhatappStatusActivity.this,MainActivity.class));
                finish();
            }
        });
        directory=new File(Environment.getExternalStorageDirectory()+"/WhatsApp/Media/.Statuses");
        statusesList=new ArrayList<>();

        listFile=directory.listFiles();
        for (int j=0;j<listFile.length;j++)
        {
            statusesList.add(listFile[j]);
        }
        filesAdapter=new FilesAdapter(statusesList,this);

        recStatusList.setLayoutManager(new GridLayoutManager(this,2));
        recStatusList.setHasFixedSize(true);
        recStatusList.setAdapter(filesAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
