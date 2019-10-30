package com.danny.dailynova;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.danny.dailynova.adapters.VideoPlayerAdapter;
import com.danny.dailynova.builders.VideoItemBuilder;
import com.danny.dailynova.items.VideoItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import cn.jzvd.JZVideoPlayerStandard;

public class PlayVideoActivity extends AppCompatActivity {
    private JZVideoPlayerStandard videoPlayer;
    Bundle bundle;
    ArrayList<VideoItem> videoItems;
    VideoPlayerAdapter adapter;
    RecyclerView recPlyerVideoList;
    ProgressBar simpleProgressBar;
    VideoItem videoItem;
    public static boolean isActive=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        videoPlayer=(JZVideoPlayerStandard)findViewById(R.id.play_in_video_player);
        bundle=getIntent().getExtras();
        if (bundle!=null){
            videoPlayer.setUp(bundle.getString("videoUrl"),
                    JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                    bundle.getString("videoName"));

            videoPlayer.startVideo();


        recPlyerVideoList=(RecyclerView)findViewById(R.id.rec_player_video_list);
        simpleProgressBar=(ProgressBar)findViewById(R.id.progress_in_play_video);
       setDataToViews();
        }
    }

    private void setDataToViews(){
        videoItem=new VideoItem(bundle.getString("videoName"),"",bundle.getString("videoSource"),"","","",0l,0l);
        videoItems=new ArrayList<>();
        videoItems.add(videoItem);
        simpleProgressBar.setVisibility(View.VISIBLE);
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
                             VideoItemBuilder builder=new VideoItemBuilder();
                             videoItem =builder.setVideoName((String)lyricsMap.get("videoName"))
                                        .setVideoSource((String)lyricsMap.get("videoSource"))
                                        .setVideoDuration((String)lyricsMap.get("videoDuration"))
                                        .setVideoLikes((Long)lyricsMap.get("videoLikes"))
                                        .setVideoDownloads((Long) lyricsMap.get("videoDownloads"))
                                        .setVideoDownloadUrl((String) lyricsMap.get("videoUrl"))
                                        .setVdeoImageUrl((String)lyricsMap.get("videoImageUri"))
                                        .setPostedOn((String) lyricsMap.get("videoUploadDate"))
                                        .build();

                            ((VideoPlayerAdapter)recPlyerVideoList.getAdapter()).updateAdapter(videoItem);
                        }catch (Exception e)
                        {
                            Toast.makeText(PlayVideoActivity.this, "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(PlayVideoActivity.this, "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter=new VideoPlayerAdapter(this,videoItems);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        layoutManager.setSmoothScrollbarEnabled(true);
        recPlyerVideoList.setLayoutManager(layoutManager);
        recPlyerVideoList.setHasFixedSize(false);
        recPlyerVideoList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isActive=true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        videoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (videoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoPlayer.releaseAllVideos();
        isActive=false;

    }

}
