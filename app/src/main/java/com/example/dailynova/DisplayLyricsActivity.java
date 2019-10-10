package com.example.dailynova;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DisplayLyricsActivity extends AppCompatActivity {
    TextView txtArtistName,lyricsName,lyricsBody;
    ImageView imgArtistImage,imgDownload,imgShare;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lyrics);
        txtArtistName=(TextView)findViewById(R.id.txt_artist_name_in_display_lyrics);
        lyricsName=(TextView)findViewById(R.id.txt_lyrics_name_in_display_lyrics);
        lyricsBody=(TextView)findViewById(R.id.txt_lyrics_body_in_display_lyrics);
        imgArtistImage=(ImageView)findViewById(R.id.img_artist_image_in_display_lyrics);
        imgDownload=(ImageView)findViewById(R.id.img_download_lyrics_in_display_lyrics);
        imgShare=(ImageView)findViewById(R.id.img_share_lyrics_in_display_lyrics);
        bundle=getIntent().getExtras();

        if (bundle!=null){
            txtArtistName.setText(bundle.getString("artist_name"));
            lyricsName.setText(bundle.getString("song_name"));
            lyricsBody.setText(bundle.getString("lyrics_body"));
            Glide.with(this).load(bundle.getString("artist_image")).placeholder(R.drawable.ic_image_black_24dp).into(imgArtistImage);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
