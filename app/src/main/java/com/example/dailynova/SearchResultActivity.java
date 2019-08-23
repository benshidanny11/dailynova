package com.example.dailynova;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailynova.adapters.HomeAdapter;
import com.example.dailynova.adapters.LifestyleAdapter;
import com.example.dailynova.adapters.LyricsAdapter;
import com.example.dailynova.adapters.VideoAdapter;
import com.example.dailynova.builders.TrendingItemBuilder;
import com.example.dailynova.items.LifeStyleItem;
import com.example.dailynova.items.LyricsItem;
import com.example.dailynova.items.TrendingItem;
import com.example.dailynova.items.VideoItem;
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
    HomeAdapter homeAdapter;
    LifeStyleItem lifeStyleItem;
    VideoItem videoItem;
    VideoAdapter videoAdapter;
    ArrayList<VideoItem> videoItems;
    LyricsItem lyrics;
    LyricsAdapter lyricsAdapter;
    ArrayList<LyricsItem> lyricsItems;
    RecyclerView recLifestyleList;
    ArrayList<LifeStyleItem> lifeStyleItems;
    LifestyleAdapter adapter;
    LifeStyleItem item;
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
                    homeItems=new ArrayList<>();
                   if (bundle!=null){

                     if(bundle.getString("fragment").equals("trending"))  {
                         progressBar.setVisibility(View.VISIBLE);
                         Query trendingQueryByTitle= FirebaseDatabase.getInstance().getReference().child("news")
                                 .orderByChild("newsTitle").startAt(txtSerch.getText().toString());
                         Query trendingQueryByBody= FirebaseDatabase.getInstance().getReference().child("news")
                                 .orderByChild("newsBody").startAt(txtSerch.getText().toString());
                         Query trendingQueryByCategory= FirebaseDatabase.getInstance().getReference().child("news")
                                 .orderByChild("newsCategory").startAt(txtSerch.getText().toString());
                         Query trendingQueryByOwner= FirebaseDatabase.getInstance().getReference().child("news")
                                 .orderByChild("newsOwner").startAt(txtSerch.getText().toString());

                         /*trendingQueryByTitle.addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 try
                                 {
                                     if (!dataSnapshot.exists()){
                                         progressBar.setVisibility(View.GONE);
                                         txtSearchResult.setVisibility(View.VISIBLE);
                                         txtSearchResult.setText(R.string.message_result_not_found);
                                         txtSearchResult.setTextColor(R.color.message_color);

                                     }
                                     for (DataSnapshot ds:dataSnapshot.getChildren())
                                     {
                                         try
                                         {
                                             txtSearchResult.setVisibility(View.VISIBLE);
                                             txtSearchResult.setText(R.string.message_result_found);
                                             txtSearchResult.setTextColor(R.color.message_color);
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
                         });*/
                         /*trendingQueryByBody.addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 try
                                 {
                                     if (!dataSnapshot.exists()){
                                         progressBar.setVisibility(View.GONE);
                                         txtSearchResult.setVisibility(View.VISIBLE);
                                         txtSearchResult.setText(R.string.message_result_not_found);
                                         txtSearchResult.setTextColor(R.color.message_color);

                                     }
                                     for (DataSnapshot ds:dataSnapshot.getChildren())
                                     {
                                         try
                                         {
                                             txtSearchResult.setVisibility(View.VISIBLE);
                                             txtSearchResult.setText(R.string.message_result_found);
                                             txtSearchResult.setTextColor(R.color.message_color);
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
                                 catch (Exception e)
                                 {
                                     Toast.makeText(SearchResultActivity.this, "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });*/
                         trendingQueryByCategory.addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 try
                                 {
                                     if (!dataSnapshot.exists()){
                                         progressBar.setVisibility(View.GONE);
                                         txtSearchResult.setVisibility(View.VISIBLE);
                                         txtSearchResult.setText(R.string.message_result_not_found);
                                         txtSearchResult.setTextColor(R.color.errorColor);

                                     }
                                     for (DataSnapshot ds:dataSnapshot.getChildren())
                                     {
                                         try
                                         {
                                             txtSearchResult.setVisibility(View.VISIBLE);
                                             txtSearchResult.setText(R.string.message_result_found);
                                             txtSearchResult.setTextColor(R.color.message_color);
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
                                 catch (Exception e)
                                 {
                                     Toast.makeText(SearchResultActivity.this, "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });
                         trendingQueryByOwner.addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 try
                                 {
                                     if (!dataSnapshot.exists()){
                                         progressBar.setVisibility(View.GONE);
                                         txtSearchResult.setVisibility(View.VISIBLE);
                                         txtSearchResult.setText(R.string.message_result_not_found);
                                         txtSearchResult.setTextColor(R.color.errorColor);

                                     }
                                     for (DataSnapshot ds:dataSnapshot.getChildren())
                                     {
                                         try
                                         {
                                             txtSearchResult.setVisibility(View.VISIBLE);
                                             txtSearchResult.setText(R.string.message_result_found);
                                             txtSearchResult.setTextColor(R.color.message_color);
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
                                 catch (Exception e)
                                 {
                                     Toast.makeText(SearchResultActivity.this, "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });
                         homeAdapter = new HomeAdapter(SearchResultActivity.this, homeItems);
                         LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(SearchResultActivity.this, 1, false);
                         recSearchResultList.setLayoutManager(localLinearLayoutManager);
                         recSearchResultList.setHasFixedSize(true);
                         recSearchResultList.setAdapter(homeAdapter);
                     }
                     else if (bundle.getString("fragment").equals("video")){
                         progressBar.setVisibility(View.VISIBLE);
                         videoItems=new ArrayList<>();
                         Query videoQueryByName= FirebaseDatabase.getInstance().getReference().child("videos")
                                 .orderByChild("videoName").startAt(txtSerch.getText().toString());
                         Query videoQueryBySource= FirebaseDatabase.getInstance().getReference().child("videos")
                                 .orderByChild("videoSource").startAt(txtSerch.getText().toString());

                         videoQueryByName.addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 try
                                 {
                                     if (!dataSnapshot.exists()){
                                         progressBar.setVisibility(View.GONE);
                                         txtSearchResult.setVisibility(View.VISIBLE);
                                         txtSearchResult.setText(R.string.message_result_not_found);
                                         txtSearchResult.setTextColor(R.color.errorColor);

                                     }


                                     for (DataSnapshot ds:dataSnapshot.getChildren())
                                     {
                                         try
                                         {

                                             progressBar.setVisibility(View.GONE);
                                             HashMap lyricsMap = (HashMap)ds.getValue();

                                             videoItem = new VideoItem((String)lyricsMap.get("videoName"), (String)lyricsMap.get("videoDuration"),
                                                     (String)lyricsMap.get("videoSource"), (String)lyricsMap.get("videoImageUri")
                                                     , (String) lyricsMap.get("videoUrl"), (String) lyricsMap.get("videoUploadDate"),(Long)lyricsMap.get("videoLikes"),(Long) lyricsMap.get("videoDownloads"));
                                             ((VideoAdapter)recSearchResultList.getAdapter()).updateAdapter(videoItem);
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

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });
                         videoAdapter = new VideoAdapter(SearchResultActivity.this, videoItems);
                         LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(SearchResultActivity.this, 1, false);
                         recSearchResultList.setLayoutManager(localLinearLayoutManager);
                         recSearchResultList.setHasFixedSize(true);
                         recSearchResultList.setAdapter(videoAdapter);

                     }

                   }

                }

            }
        });
    }
}
