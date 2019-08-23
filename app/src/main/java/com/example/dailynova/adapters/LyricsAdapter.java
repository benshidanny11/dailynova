package com.example.dailynova.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dailynova.DisplayLyricsActivity;
import com.example.dailynova.R;
import com.example.dailynova.items.LyricsItem;

import java.util.ArrayList;

public class LyricsAdapter extends RecyclerView.Adapter<LyricsAdapter.LyricsHolder> {
   private Context context;
   private ArrayList<LyricsItem> lyricsItems;

    public LyricsAdapter(Context context, ArrayList<LyricsItem> lyricsItems) {
        this.context = context;
        this.lyricsItems = lyricsItems;
    }

    @NonNull
    @Override
    public LyricsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return  new LyricsHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_lyrics, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LyricsHolder lyricsHolder, int i) {
          final LyricsItem lyricsItem=lyricsItems.get(i);
          lyricsHolder.txtLyricsName.setText(lyricsItem.getLyricsName());
          lyricsHolder.txtLyricsArtistName.setText(lyricsItem.getArtistName());
          lyricsHolder.txtPostedOn.setText(lyricsItem.getPostedOn());
          Glide
            .with(context)
            .load(lyricsItem.getImgUrl())
            .centerCrop()
            .placeholder(R.drawable.loading)
            .into(lyricsHolder.imgArtistImage);
          lyricsHolder.imgMore.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

              }
          });
          lyricsHolder.imgDownloadLyrics.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

              }
          });
          lyricsHolder.imgShareLyrics.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

              }
          });
          lyricsHolder.imgArtistImage.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent displayIntent=new Intent(context, DisplayLyricsActivity.class);
                  displayIntent.putExtra("artist_name",lyricsItem.getArtistName());
                  displayIntent.putExtra("song_name",lyricsItem.getLyricsName());
                  displayIntent.putExtra("artist_image",lyricsItem.getImgUrl());

                  displayIntent.putExtra("lyrics_body",lyricsItem.getSongBody());
                  displayIntent.putExtra("lyrics_url",lyricsItem.getSongUrl());
                  context.startActivity(displayIntent);
              }
          });

    }

    @Override
    public int getItemCount() {
        return lyricsItems.size();
    }

    public void updateAdapter(LyricsItem lyrics) {
        lyricsItems.add(lyrics);
        notifyDataSetChanged();
    }


    class LyricsHolder extends RecyclerView.ViewHolder{

        private TextView txtLyricsName,txtLyricsArtistName,txtPostedOn;
        private ImageView imgMore,imgArtistImage,imgDownloadLyrics,imgShareLyrics;
        public LyricsHolder(@NonNull View itemView) {
            super(itemView);
            imgArtistImage=(ImageView) itemView.findViewById(R.id.img_artist_image_in_row_lyrics);
            imgDownloadLyrics=(ImageView) itemView.findViewById(R.id.img_download_lyrics_audio_in_row_lyrics);
            imgMore=(ImageView)itemView.findViewById(R.id.img_more_in_row_lyrics);
            imgShareLyrics=(ImageView)itemView.findViewById(R.id.img_share_lyrics_audio_in_row_lyrics);
            txtPostedOn=(TextView)itemView.findViewById(R.id.txt_posted_on_in_row_lyrics);
            txtLyricsArtistName=(TextView)itemView.findViewById(R.id.txt_artist_name_in_row_lyrics);
            txtLyricsName=(TextView)itemView.findViewById(R.id.txt_lyrics_name_in_lyrics_row);
        }
    }

}
