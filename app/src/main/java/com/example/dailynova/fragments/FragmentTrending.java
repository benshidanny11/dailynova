package com.example.dailynova.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dailynova.R;
import com.example.dailynova.WhatappStatusActivity;
import com.example.dailynova.adapters.HomeAdapter;
import com.example.dailynova.builders.TrendingItemBuilder;
import com.example.dailynova.items.LifeStyleItem;
import com.example.dailynova.items.TrendingItem;
import com.example.dailynova.utils.IntentUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentTrending extends Fragment {
    ProgressBar progressBar;
    RecyclerView recHomeItemsList;
    ArrayList<TrendingItem> homeItems;
    HomeAdapter homeAdapter;
    LifeStyleItem lifeStyleItem;
    ImageView imgOpenStatusDownloader,imgOpenFacebook,imgOpenInstagram;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_trending,container,false);
        recHomeItemsList=(RecyclerView)rootView.findViewById(R.id.rec_trending_list);
        progressBar=(ProgressBar)rootView.findViewById(R.id.progress_in_fragment_trending);
        imgOpenStatusDownloader=(ImageView) rootView.findViewById(R.id.img_open_whatapp_statuses_in_fragment_trending);
        imgOpenFacebook=(ImageView)rootView.findViewById(R.id.img_open_facebook_in_fragment_trending);
        imgOpenInstagram=(ImageView)rootView.findViewById(R.id.img_open_instagram_in_fragment_trending);
        imgOpenStatusDownloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStatusDownloader();
            }
        });
        imgOpenFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Please wait a meanwhile", Toast.LENGTH_SHORT).show();
                startActivity(IntentUtil.getOpenFacebookIntent(getActivity()));
            }
        });
        imgOpenInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(IntentUtil.getOpenInstagramIntent(getActivity()));
                //Toast.makeText(getActivity(), "Please wait a meanwhile", Toast.LENGTH_SHORT).show();
            }
        });
        loadHomeData();
        return rootView;
    }
    private void loadHomeData(){
        progressBar.setVisibility(View.VISIBLE);
        homeItems=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("news").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                try
                {
                    for (DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        try
                        {
                            progressBar.setVisibility(View.GONE);
                            HashMap lyricsMap = (HashMap)ds.getValue();
                            TrendingItemBuilder itemBuilder=new TrendingItemBuilder();
                            final TrendingItem trendingItem = itemBuilder.setNewsTitle((String)lyricsMap.get("newsTitle"))
                                                        .setNewsBody((String)lyricsMap.get("newsBody"))
                                                        .setNewsOwner((String)lyricsMap.get("newsOwner"))
                                                        .setNewsCategory((String)lyricsMap.get("newsCategory"))
                                                        .setNewsImgDownloadLink((String) lyricsMap.get("newsImageUrl"))
                                                        .setPostedOn((String) lyricsMap.get("postedOn"))
                                                        .buildNewsItem();
                            ((HomeAdapter)recHomeItemsList.getAdapter()).upateAdapter(trendingItem);

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
        homeAdapter = new HomeAdapter(getActivity(), homeItems);
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(getActivity(), 1, false);
        recHomeItemsList.setLayoutManager(localLinearLayoutManager);
        recHomeItemsList.setHasFixedSize(true);
        recHomeItemsList.setAdapter(homeAdapter);
    }
    private void openStatusDownloader() {
        Intent intent=new Intent(getActivity(), WhatappStatusActivity.class);
        getActivity().startActivity(intent);
    }

}
