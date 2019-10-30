package com.danny.dailynova.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.danny.dailynova.PlayVideoActivity;

public class PlayActivityUtil extends Activity {
    Bundle bundle;
    @Override
    protected void onStart() {
        super.onStart();
        bundle=getIntent().getExtras();
        if (bundle!=null){
            Intent playIntent=new Intent(this, PlayVideoActivity.class);

            playIntent.putExtra("videoUrl",bundle.getString("videoUrl"));
            playIntent.putExtra("videoName",bundle.getString("videoName"));
            playIntent.putExtra("videoDuration",bundle.getString("videoDuration"));
            playIntent.putExtra("videoLikes",bundle.getString("videoLikes"));
            playIntent.putExtra("videoDownloads",bundle.getString("videoDownloads"));
            playIntent.putExtra("videoSource",bundle.getString("videoSource"));
            startActivity(playIntent);
            finish();
        }
    }
}
