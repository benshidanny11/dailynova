package com.example.dailynova.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dailynova.R;
import com.example.dailynova.WhatappStatusActivity;
import com.example.dailynova.adapters.VideoAdapter;
import com.example.dailynova.items.VideoItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentVideo extends Fragment {
    ProgressBar simpleProgressBar;
    VideoItem videoItem;
    VideoAdapter videoAdapter;
    ArrayList<VideoItem> videoItems;
    RecyclerView recVideoList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_videos,container,false);
        simpleProgressBar=(ProgressBar)rootView.findViewById(R.id.progres_in_fragment_video);
        recVideoList=(RecyclerView)rootView.findViewById(R.id.rec_video_list);

        getData();
        return rootView;
    }



    public void getData(){
        simpleProgressBar.setVisibility(View.VISIBLE);
        videoItems=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("videos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                try
                {
                    for (DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        try
                        {
                            simpleProgressBar.setVisibility(View.GONE);
                            HashMap lyricsMap = (HashMap)ds.getValue();

                            videoItem = new VideoItem((String)lyricsMap.get("videoName"), (String)lyricsMap.get("videoDuration"),
                                    (String)lyricsMap.get("videoSource"), (String)lyricsMap.get("videoImageUri")
                                    , (String) lyricsMap.get("videoUrl"), (String) lyricsMap.get("videoUploadDate"),(Long)lyricsMap.get("videoLikes"),(Long) lyricsMap.get("videoDownloads"));
                            ((VideoAdapter)recVideoList.getAdapter()).updateAdapter(videoItem);
                        }catch (Exception e)
                        {
                            Toast.makeText(getActivity(), "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        videoAdapter = new VideoAdapter(getActivity(), videoItems);
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(getActivity(), 1, false);
        recVideoList.setLayoutManager(localLinearLayoutManager);
        recVideoList.setHasFixedSize(true);
        recVideoList.setAdapter(videoAdapter);
    }
}
