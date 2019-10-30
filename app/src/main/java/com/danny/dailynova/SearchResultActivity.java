package com.danny.dailynova;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.danny.dailynova.adapters.BusinessAdapter;
import com.danny.dailynova.adapters.HomeAdapter;
import com.danny.dailynova.adapters.LifestyleAdapter;
import com.danny.dailynova.adapters.LyricsAdapter;
import com.danny.dailynova.adapters.VideoAdapter;
import com.danny.dailynova.builders.LifestyleBuilder;
import com.danny.dailynova.builders.TrendingItemBuilder;
import com.danny.dailynova.items.BusinessItem;
import com.danny.dailynova.items.LifeStyleItem;
import com.danny.dailynova.items.LyricsItem;
import com.danny.dailynova.items.TrendingItem;
import com.danny.dailynova.items.VideoItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultActivity extends AppCompatActivity {

    ImageView imgBack,imgSearch;
    EditText txtSerch;
    TextView txtSearchResult;
    Bundle bundle;
    ProgressBar progressBar;
    RecyclerView recSearchResultList;
    ArrayList<TrendingItem> homeItems;
    private HomeAdapter homeAdapter;
    LifeStyleItem lifeStyleItem;
    VideoItem videoItem;
    VideoAdapter videoAdapter;
    ArrayList<VideoItem> videoItems;
    LyricsItem lyrics;
    LyricsAdapter lyricsAdapter;
    ArrayList<LyricsItem> lyricsItems;
    ArrayList<LifeStyleItem> lifeStyleItems;
    LifestyleAdapter adapter;
    LifeStyleItem item;
    BusinessAdapter businessAdapter;
    BusinessItem businessItem;
    ArrayList<BusinessItem> businessItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
//        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_in_search_activity);
//        setSupportActionBar(toolbar);
        imgBack=(ImageView)findViewById(R.id.img_action_back_in_search_activity);
        imgSearch=(ImageView)findViewById(R.id.img_action_search_in_search_activity);
        txtSerch=(EditText)findViewById(R.id.txt_search_in_search_activity);
        txtSearchResult=(TextView)findViewById(R.id.txt_result_message_in_search_activity);
        progressBar=(ProgressBar)findViewById(R.id.progress_in_search_activity);
        recSearchResultList=(RecyclerView)findViewById(R.id.rec_search_result_list);
        bundle=getIntent().getExtras();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchResultActivity.this,MainActivity.class));
                finish();
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtSerch.getText())||txtSerch.getText().toString().equals("")){
                    txtSearchResult.setVisibility(View.VISIBLE);
                  txtSearchResult.setText(R.string.error_no_input);
                  txtSearchResult.setTextColor(R.color.errorColor);
                }
                else{
                   if (bundle!=null){

                     if(bundle.getString("fragment").equals("trending"))  {
                         homeItems=new ArrayList<>();
                         progressBar.setVisibility(View.VISIBLE);
                         Query trendingQueryByTitle= FirebaseDatabase.getInstance().getReference().child("news")
                                 .orderByChild("newsTitle").startAt(txtSerch.getText().toString());
                         final Query trendingQueryByBody= FirebaseDatabase.getInstance().getReference().child("news")
                                 .orderByChild("newsBody").startAt(txtSerch.getText().toString());
                         final Query trendingQueryByCategory= FirebaseDatabase.getInstance().getReference().child("news")
                                 .orderByChild("newsCategory").startAt(txtSerch.getText().toString());
                         final Query trendingQueryByOwner= FirebaseDatabase.getInstance().getReference().child("news")
                                 .orderByChild("newsOwner").startAt(txtSerch.getText().toString());

                         trendingQueryByTitle.addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 try
                                 {
                                     if (!dataSnapshot.exists()){
                                       trendingQueryByOwner.addListenerForSingleValueEvent(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                               if(!dataSnapshot.exists()){
                                                  trendingQueryByBody.addListenerForSingleValueEvent(new ValueEventListener() {
                                                      @Override
                                                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                          if (!dataSnapshot.exists()){
                                                              trendingQueryByCategory.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                  @Override
                                                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                      if (!dataSnapshot.exists()){
                                                                          progressBar.setVisibility(View.GONE);
                                                                          txtSearchResult.setVisibility(View.VISIBLE);
                                                                          txtSearchResult.setText(R.string.message_result_not_found);
                                                                          txtSearchResult.setTextColor(R.color.message_color);
                                                                      }
                                                                      else{
                                                                          for (DataSnapshot ds:dataSnapshot.getChildren())
                                                                          {
                                                                              try
                                                                              {
                                                                                  txtSearchResult.setVisibility(View.GONE);
                                                                                  txtSerch.setText(null);
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
                                                                                  ((HomeAdapter)recSearchResultList.getAdapter()).upateAdapter(trendingItem);

                                                                              }catch (Exception e)
                                                                              {
                                                                                  Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                              }
                                                                          }
                                                                      }
                                                                  }

                                                                  @Override
                                                                  public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                  }
                                                              });
                                                          }else {
                                                              for (DataSnapshot ds:dataSnapshot.getChildren())
                                                              {
                                                                  try
                                                                  {
                                                                      txtSearchResult.setVisibility(View.GONE);
                                                                      txtSerch.setText(null);
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
                                                                      ((HomeAdapter)recSearchResultList.getAdapter()).upateAdapter(trendingItem);

                                                                  }catch (Exception e)
                                                                  {
                                                                      Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                  }
                                                              }
                                                          }
                                                      }

                                                      @Override
                                                      public void onCancelled(@NonNull DatabaseError databaseError) {

                                                      }
                                                  });
                                               }else{
                                                   for (DataSnapshot ds:dataSnapshot.getChildren())
                                                   {
                                                       try
                                                       {
                                                           txtSearchResult.setVisibility(View.GONE);
                                                           txtSerch.setText(null);
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
                                                           ((HomeAdapter)recSearchResultList.getAdapter()).upateAdapter(trendingItem);

                                                       }catch (Exception e)
                                                       {
                                                           Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                       }
                                                   }
                                               }
                                           }

                                           @Override
                                           public void onCancelled(@NonNull DatabaseError databaseError) {

                                           }
                                       });

                                     }
                                     else{
                                         for (DataSnapshot ds:dataSnapshot.getChildren())
                                         {
                                             try
                                             {
                                                 txtSearchResult.setVisibility(View.GONE);
                                                 txtSerch.setText(null);
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
                                                 ((HomeAdapter)recSearchResultList.getAdapter()).upateAdapter(trendingItem);

                                             }catch (Exception e)
                                             {
                                                 Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
                                 }
                                 catch (Exception e)
                                 {
                                     Toast.makeText(SearchResultActivity.this, "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {
                              progressBar.setVisibility(View.GONE);
                                 Toast.makeText(SearchResultActivity.this, "No result found", Toast.LENGTH_SHORT).show();
                             }
                         });
                         homeAdapter = new HomeAdapter(SearchResultActivity.this, homeItems);
                         LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(SearchResultActivity.this, RecyclerView.VERTICAL, false);
                         recSearchResultList.setLayoutManager(localLinearLayoutManager);
                         recSearchResultList.setHasFixedSize(true);
                         recSearchResultList.setAdapter(homeAdapter);
                     }
                     else if (bundle.getString("fragment").equals("video")){
                         progressBar.setVisibility(View.VISIBLE);
                         videoItems=new ArrayList<>();
                         Query videoQueryByName= FirebaseDatabase.getInstance().getReference().child("videos")
                                 .orderByChild("videoName").startAt(txtSerch.getText().toString());
                         final Query videoQueryBySource= FirebaseDatabase.getInstance().getReference().child("videos")
                                 .orderByChild("videoSource").startAt(txtSerch.getText().toString());

                         videoQueryByName.addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 try
                                 {
                                     if (!dataSnapshot.exists()){

                                       videoQueryBySource.addListenerForSingleValueEvent(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                               if (!dataSnapshot.exists()){
                                                   progressBar.setVisibility(View.GONE);
                                                   txtSearchResult.setVisibility(View.VISIBLE);
                                                   txtSearchResult.setText(R.string.message_result_not_found);
                                                   txtSearchResult.setTextColor(R.color.errorColor);
                                               }
                                               else {
                                                   for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                       try {
                                                           progressBar.setVisibility(View.GONE);
                                                           HashMap lyricsMap = (HashMap) ds.getValue();

                                                           videoItem = new VideoItem((String) lyricsMap.get("videoName"), (String) lyricsMap.get("videoDuration"),
                                                                   (String) lyricsMap.get("videoSource"), (String) lyricsMap.get("videoImageUri")
                                                                   , (String) lyricsMap.get("videoUrl"), (String) lyricsMap.get("videoUploadDate"), (Long) lyricsMap.get("videoLikes"), (Long) lyricsMap.get("videoDownloads"));
                                                           ((VideoAdapter) recSearchResultList.getAdapter()).updateAdapter(videoItem);
                                                       } catch (Exception e) {
                                                           Toast.makeText(SearchResultActivity.this, "In load data for loop: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                       }
                                                   }
                                               }
                                           }

                                           @Override
                                           public void onCancelled(@NonNull DatabaseError databaseError) {

                                           }
                                       });
                                     }else {
                                         for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                             try {
                                                 progressBar.setVisibility(View.GONE);
                                                 HashMap lyricsMap = (HashMap) ds.getValue();

                                                 videoItem = new VideoItem((String) lyricsMap.get("videoName"), (String) lyricsMap.get("videoDuration"),
                                                         (String) lyricsMap.get("videoSource"), (String) lyricsMap.get("videoImageUri")
                                                         , (String) lyricsMap.get("videoUrl"), (String) lyricsMap.get("videoUploadDate"), (Long) lyricsMap.get("videoLikes"), (Long) lyricsMap.get("videoDownloads"));
                                                 ((VideoAdapter) recSearchResultList.getAdapter()).updateAdapter(videoItem);
                                             } catch (Exception e) {
                                                 Toast.makeText(SearchResultActivity.this, "In load data for loop: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
                                 }
                                 catch (Exception e)
                                 {
                                     Toast.makeText(SearchResultActivity.this, "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });

                         videoAdapter = new VideoAdapter(SearchResultActivity.this, videoItems);
                         LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(SearchResultActivity.this, RecyclerView.VERTICAL, false);
                         recSearchResultList.setLayoutManager(localLinearLayoutManager);
                         recSearchResultList.setHasFixedSize(true);
                         recSearchResultList.setAdapter(videoAdapter);

                     }
                     else if(bundle.getString("fragment").equals("lyrics")){
                         lyricsItems=new ArrayList<>();
                       Query lyricsQueryByName=FirebaseDatabase.getInstance().getReference()
                               .child("songs").child("lyrics_songs").orderByChild("songName")
                               .startAt(txtSerch.getText().toString());
                         final Query lyricsQueryByArtist=FirebaseDatabase.getInstance().getReference()
                                 .child("songs").child("lyrics_songs").orderByChild("artistName")
                                 .startAt(txtSerch.getText().toString());
                         final Query lyricsQueryByBody=FirebaseDatabase.getInstance().getReference()
                                 .child("songs").child("lyrics_songs").orderByChild("songLyrics")
                                 .startAt(txtSerch.getText().toString());
                         lyricsQueryByName.addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 if (!dataSnapshot.exists()){
                                   lyricsQueryByArtist.addListenerForSingleValueEvent(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                           if (!dataSnapshot.exists()){
                                             lyricsQueryByBody.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                     if (!dataSnapshot.exists()){
                                                         progressBar.setVisibility(View.GONE);
                                                         txtSearchResult.setVisibility(View.VISIBLE);
                                                         txtSearchResult.setText(R.string.message_result_not_found);
                                                         txtSearchResult.setTextColor(R.color.errorColor);
                                                     }else {
                                                         for (DataSnapshot ds:dataSnapshot.getChildren())
                                                         {
                                                             try
                                                             {
                                                                 progressBar.setVisibility(View.GONE);
                                                                 HashMap lyricsMap = (HashMap)ds.getValue();

                                                                 lyrics = new LyricsItem((String)lyricsMap.get("songName"), (String)lyricsMap.get("artistName"),
                                                                         (String)lyricsMap.get("artistImageUri"), (String)lyricsMap.get("songLyrics")
                                                                         , (String) lyricsMap.get("songUri"), (String) lyricsMap.get("postedOn"));
                                                                 ((LyricsAdapter)recSearchResultList.getAdapter()).updateAdapter(lyrics);
                                                             }catch (Exception e)
                                                             {
                                                                 Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                             }
                                                         }
                                                     }
                                                 }

                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                                 }
                                             });
                                           }else{
                                               for (DataSnapshot ds:dataSnapshot.getChildren())
                                               {
                                                   try
                                                   {
                                                       progressBar.setVisibility(View.GONE);
                                                       HashMap lyricsMap = (HashMap)ds.getValue();

                                                       lyrics = new LyricsItem((String)lyricsMap.get("songName"), (String)lyricsMap.get("artistName"),
                                                               (String)lyricsMap.get("artistImageUri"), (String)lyricsMap.get("songLyrics")
                                                               , (String) lyricsMap.get("songUri"), (String) lyricsMap.get("postedOn"));
                                                       ((LyricsAdapter)recSearchResultList.getAdapter()).updateAdapter(lyrics);
                                                   }catch (Exception e)
                                                   {
                                                       Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                   }
                                               }
                                           }
                                       }

                                       @Override
                                       public void onCancelled(@NonNull DatabaseError databaseError) {

                                       }
                                   });
                                 }else{
                                     for (DataSnapshot ds:dataSnapshot.getChildren())
                                     {
                                         try
                                         {
                                             progressBar.setVisibility(View.GONE);
                                             HashMap lyricsMap = (HashMap)ds.getValue();

                                             lyrics = new LyricsItem((String)lyricsMap.get("songName"), (String)lyricsMap.get("artistName"),
                                                     (String)lyricsMap.get("artistImageUri"), (String)lyricsMap.get("songLyrics")
                                                     , (String) lyricsMap.get("songUri"), (String) lyricsMap.get("postedOn"));
                                             ((LyricsAdapter)recSearchResultList.getAdapter()).updateAdapter(lyrics);
                                         }catch (Exception e)
                                         {
                                             Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                         }
                                     }
                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });
                         lyricsAdapter = new LyricsAdapter(SearchResultActivity.this, lyricsItems);
                         LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(SearchResultActivity.this, RecyclerView.VERTICAL, false);
                         recSearchResultList.setLayoutManager(localLinearLayoutManager);
                         recSearchResultList.setHasFixedSize(true);
                         recSearchResultList.setAdapter(lyricsAdapter);

                     }
                     else if(bundle.getString("fragment").equals("lifestyle")){
                         lifeStyleItems=new ArrayList<>();
                         Query queryLifesyleByTitle=FirebaseDatabase.getInstance().getReference().child("lifestyle")
                                 .orderByChild("lifeStyleTitle");
                         final Query queryLifesyleBySource=FirebaseDatabase.getInstance().getReference().child("lifestyle")
                                 .orderByChild("lifestyleSource");
                         final Query queryLifesyleByBody=FirebaseDatabase.getInstance().getReference().child("lifestyle")
                                 .orderByChild("lifeStyleContent");

                         final Query queryLifesyleByCategory=FirebaseDatabase.getInstance().getReference().child("lifestyle")
                                 .orderByChild("lifeStyleCategory");

                         queryLifesyleByTitle.addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 if (!dataSnapshot.exists()){
                                     queryLifesyleBySource.addListenerForSingleValueEvent(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                             if (!dataSnapshot.exists()){
                                                queryLifesyleByBody.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (!dataSnapshot.exists()){
                                                            queryLifesyleByCategory.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    if (!dataSnapshot.exists()){
                                                                        progressBar.setVisibility(View.GONE);
                                                                        txtSearchResult.setVisibility(View.VISIBLE);
                                                                        txtSearchResult.setText(R.string.message_result_not_found);
                                                                        txtSearchResult.setTextColor(R.color.errorColor);
                                                                    }else{
                                                                        progressBar.setVisibility(View.GONE);
                                                                        txtSearchResult.setVisibility(View.GONE);
                                                                        try
                                                                        {
                                                                            for (DataSnapshot ds:dataSnapshot.getChildren())
                                                                            {
                                                                                try
                                                                                {
                                                                                    progressBar.setVisibility(View.GONE);
                                                                                    HashMap lifeStyleMap = (HashMap)ds.getValue();

                                                                                    item = new LifestyleBuilder()
                                                                                            .setLifeStyleTitle((String)lifeStyleMap.get("lifeStyleTitle"))
                                                                                            .setLifestyleContent((String)lifeStyleMap.get("lifeStyleContent"))
                                                                                            .setLifestyleSource((String)lifeStyleMap.get("lifestyleSource"))
                                                                                            .setLifestyleUploadDate((String)lifeStyleMap.get("postedOn"))
                                                                                            .setLifestyleImageUrl((String)lifeStyleMap.get("lifeStyleImageUrl"))
                                                                                            .setLifestyleCategory((String)lifeStyleMap.get("lifeStyleCategory"))
                                                                                            .build();


                                                                                    ((LifestyleAdapter)recSearchResultList.getAdapter()).updateAdapter(item);
                                                                                }catch (Exception e)
                                                                                {
                                                                                    Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        }
                                                                        catch (Exception e)
                                                                        {
                                                                            Toast.makeText(SearchResultActivity.this, "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });
                                                        }else {
                                                            progressBar.setVisibility(View.GONE);
                                                            txtSearchResult.setVisibility(View.GONE);
                                                            try
                                                            {
                                                                for (DataSnapshot ds:dataSnapshot.getChildren())
                                                                {
                                                                    try
                                                                    {
                                                                        progressBar.setVisibility(View.GONE);
                                                                        HashMap lifeStyleMap = (HashMap)ds.getValue();

                                                                        item = new LifestyleBuilder()
                                                                                .setLifeStyleTitle((String)lifeStyleMap.get("lifeStyleTitle"))
                                                                                .setLifestyleContent((String)lifeStyleMap.get("lifeStyleContent"))
                                                                                .setLifestyleSource((String)lifeStyleMap.get("lifestyleSource"))
                                                                                .setLifestyleUploadDate((String)lifeStyleMap.get("postedOn"))
                                                                                .setLifestyleImageUrl((String)lifeStyleMap.get("lifeStyleImageUrl"))
                                                                                .setLifestyleCategory((String)lifeStyleMap.get("lifeStyleCategory"))
                                                                                .build();


                                                                        ((LifestyleAdapter)recSearchResultList.getAdapter()).updateAdapter(item);
                                                                    }catch (Exception e)
                                                                    {
                                                                        Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            }
                                                            catch (Exception e)
                                                            {
                                                                Toast.makeText(SearchResultActivity.this, "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                             }else {
                                                 progressBar.setVisibility(View.GONE);
                                                 txtSearchResult.setVisibility(View.GONE);
                                                 try
                                                 {
                                                     for (DataSnapshot ds:dataSnapshot.getChildren())
                                                     {
                                                         try
                                                         {
                                                             progressBar.setVisibility(View.GONE);
                                                             HashMap lifeStyleMap = (HashMap)ds.getValue();

                                                             item = new LifestyleBuilder()
                                                                     .setLifeStyleTitle((String)lifeStyleMap.get("lifeStyleTitle"))
                                                                     .setLifestyleContent((String)lifeStyleMap.get("lifeStyleContent"))
                                                                     .setLifestyleSource((String)lifeStyleMap.get("lifestyleSource"))
                                                                     .setLifestyleUploadDate((String)lifeStyleMap.get("postedOn"))
                                                                     .setLifestyleImageUrl((String)lifeStyleMap.get("lifeStyleImageUrl"))
                                                                     .setLifestyleCategory((String)lifeStyleMap.get("lifeStyleCategory"))
                                                                     .build();


                                                             ((LifestyleAdapter)recSearchResultList.getAdapter()).updateAdapter(item);
                                                         }catch (Exception e)
                                                         {
                                                             Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                         }
                                                     }
                                                 }
                                                 catch (Exception e)
                                                 {
                                                     Toast.makeText(SearchResultActivity.this, "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                         }

                                         @Override
                                         public void onCancelled(@NonNull DatabaseError databaseError) {

                                         }
                                     });
                                 }else{
                                     progressBar.setVisibility(View.GONE);
                                     txtSearchResult.setVisibility(View.GONE);
                                     try
                                     {
                                         for (DataSnapshot ds:dataSnapshot.getChildren())
                                         {
                                             try
                                             {
                                                 progressBar.setVisibility(View.GONE);
                                                 HashMap lifeStyleMap = (HashMap)ds.getValue();

                                                 item = new LifestyleBuilder()
                                                         .setLifeStyleTitle((String)lifeStyleMap.get("lifeStyleTitle"))
                                                         .setLifestyleContent((String)lifeStyleMap.get("lifeStyleContent"))
                                                         .setLifestyleSource((String)lifeStyleMap.get("lifestyleSource"))
                                                         .setLifestyleUploadDate((String)lifeStyleMap.get("postedOn"))
                                                         .setLifestyleImageUrl((String)lifeStyleMap.get("lifeStyleImageUrl"))
                                                         .setLifestyleCategory((String)lifeStyleMap.get("lifeStyleCategory"))
                                                         .build();


                                                 ((LifestyleAdapter)recSearchResultList.getAdapter()).updateAdapter(item);
                                             }catch (Exception e)
                                             {
                                                 Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
                                     catch (Exception e)
                                     {
                                         Toast.makeText(SearchResultActivity.this, "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });
                         adapter = new LifestyleAdapter(SearchResultActivity.this, lifeStyleItems);
                         LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(SearchResultActivity.this, RecyclerView.VERTICAL, false);
                         recSearchResultList.setLayoutManager(localLinearLayoutManager);
                         recSearchResultList.setHasFixedSize(true);
                         recSearchResultList.setAdapter(adapter);
                     }
                     else if (bundle.getString("fragment").equals("business")){
                       businessItems=new ArrayList<>();
                       progressBar.setVisibility(View.VISIBLE);
                         Query businessQueryByTitle= FirebaseDatabase.getInstance().getReference().child("business")
                                 .orderByChild("businessTitle").startAt(txtSerch.getText().toString());
                          final Query businessQueryByBody= FirebaseDatabase.getInstance().getReference().child("business")
                                 .orderByChild("businessContent").startAt(txtSerch.getText().toString());
                          final Query businessQueryByCategory= FirebaseDatabase.getInstance().getReference().child("business")
                                 .orderByChild("businessCategory").startAt(txtSerch.getText().toString());
                          final Query businessQueryByOwner= FirebaseDatabase.getInstance().getReference().child("business")
                                 .orderByChild("businessSource").startAt(txtSerch.getText().toString());
                         businessQueryByTitle.addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 if (!dataSnapshot.exists()){
                                     businessQueryByBody.addListenerForSingleValueEvent(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                             if (!dataSnapshot.exists()){
                                                 businessQueryByCategory.addListenerForSingleValueEvent(new ValueEventListener() {
                                                     @Override
                                                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                         if (!dataSnapshot.exists()){
                                                             businessQueryByOwner.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    if (!dataSnapshot.exists()){
                                                                        progressBar.setVisibility(View.GONE);
                                                                        txtSearchResult.setVisibility(View.VISIBLE);
                                                                        txtSearchResult.setText(R.string.message_result_not_found);
                                                                        txtSearchResult.setTextColor(R.color.errorColor);
                                                                    }else {
                                                                        try
                                                                        {
                                                                            for (DataSnapshot ds:dataSnapshot.getChildren())
                                                                            {
                                                                                try
                                                                                {
                                                                                    progressBar.setVisibility(View.GONE);
                                                                                    HashMap lyricsMap = (HashMap)ds.getValue();
                                                                                    businessItem = new BusinessItem((String)lyricsMap.get("businessTitle"), (String)lyricsMap.get("businessContent"),
                                                                                            (String)lyricsMap.get("businessImageUrl"),(String)lyricsMap.get("businessUploadDate")
                                                                                            , (String) lyricsMap.get("businessSource"), (String) lyricsMap.get("newsImageUrl"));
                                                                                    ((BusinessAdapter)recSearchResultList.getAdapter()).updateAdapter(businessItem);
                                                                                }catch (Exception e)
                                                                                {
                                                                                    Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        }
                                                                        catch (Exception e)
                                                                        {
                                                                            Toast.makeText(SearchResultActivity.this, "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });
                                                         }else {
                                                             try
                                                             {
                                                                 for (DataSnapshot ds:dataSnapshot.getChildren())
                                                                 {
                                                                     try
                                                                     {
                                                                         progressBar.setVisibility(View.GONE);
                                                                         HashMap lyricsMap = (HashMap)ds.getValue();
                                                                         businessItem = new BusinessItem((String)lyricsMap.get("businessTitle"), (String)lyricsMap.get("businessContent"),
                                                                                 (String)lyricsMap.get("businessImageUrl"),(String)lyricsMap.get("businessUploadDate")
                                                                                 , (String) lyricsMap.get("businessSource"), (String) lyricsMap.get("newsImageUrl"));
                                                                         ((BusinessAdapter)recSearchResultList.getAdapter()).updateAdapter(businessItem);
                                                                     }catch (Exception e)
                                                                     {
                                                                         Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                     }
                                                                 }
                                                             }
                                                             catch (Exception e)
                                                             {
                                                                 Toast.makeText(SearchResultActivity.this, "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                             }
                                                         }
                                                     }

                                                     @Override
                                                     public void onCancelled(@NonNull DatabaseError databaseError) {

                                                     }
                                                 });
                                             }else {
                                                 try
                                                 {
                                                     for (DataSnapshot ds:dataSnapshot.getChildren())
                                                     {
                                                         try
                                                         {
                                                             progressBar.setVisibility(View.GONE);
                                                             HashMap lyricsMap = (HashMap)ds.getValue();
                                                             businessItem = new BusinessItem((String)lyricsMap.get("businessTitle"), (String)lyricsMap.get("businessContent"),
                                                                     (String)lyricsMap.get("businessImageUrl"),(String)lyricsMap.get("businessUploadDate")
                                                                     , (String) lyricsMap.get("businessSource"), (String) lyricsMap.get("newsImageUrl"));
                                                             ((BusinessAdapter)recSearchResultList.getAdapter()).updateAdapter(businessItem);
                                                         }catch (Exception e)
                                                         {
                                                             Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                         }
                                                     }
                                                 }
                                                 catch (Exception e)
                                                 {
                                                     Toast.makeText(SearchResultActivity.this, "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                         }

                                         @Override
                                         public void onCancelled(@NonNull DatabaseError databaseError) {

                                         }
                                     });
                                 }else{
                                     try
                                     {
                                         for (DataSnapshot ds:dataSnapshot.getChildren())
                                         {
                                             try
                                             {
                                                 progressBar.setVisibility(View.GONE);
                                                 HashMap lyricsMap = (HashMap)ds.getValue();

                                                 businessItem = new BusinessItem((String)lyricsMap.get("businessTitle"), (String)lyricsMap.get("businessContent"),
                                                         (String)lyricsMap.get("businessImageUrl"),(String)lyricsMap.get("businessUploadDate")
                                                         , (String) lyricsMap.get("businessSource"), (String) lyricsMap.get("newsImageUrl"));
                                                 ((BusinessAdapter)recSearchResultList.getAdapter()).updateAdapter(businessItem);
                                             }catch (Exception e)
                                             {
                                                 Toast.makeText(SearchResultActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
                                     catch (Exception e)
                                     {
                                         Toast.makeText(SearchResultActivity.this, "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });

                         businessAdapter = new BusinessAdapter(SearchResultActivity.this, businessItems);
                         LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(SearchResultActivity.this, RecyclerView.VERTICAL, false);
                         recSearchResultList.setLayoutManager(localLinearLayoutManager);
                         recSearchResultList.setHasFixedSize(true);
                         recSearchResultList.setAdapter(businessAdapter);
                     }
                   }

                }

            }
        });
    }
}
