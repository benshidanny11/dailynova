package com.danny.dailynova.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.danny.dailynova.DisplayLyricsActivity;
import com.danny.dailynova.R;
import com.danny.dailynova.items.LyricsItem;
import com.danny.dailynova.utils.DownloadTaskUtil;

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
    public void onBindViewHolder(@NonNull final LyricsHolder lyricsHolder, int i) {
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
                  PopupMenu menu=new PopupMenu(context,v);
                  menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                      @Override
                      public boolean onMenuItemClick(MenuItem item) {

                          switch (item.getItemId()){
                              case R.id.mnu_listen_to_the_song:
                                  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(lyricsItem.getSongUrl()));
                                  intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                  context.startActivity(intent);
                                  return true;
                              case R.id.mnu_read_lyrics:
                                  Intent displayIntent=new Intent(context, DisplayLyricsActivity.class);
                                  displayIntent.putExtra("artist_name",lyricsItem.getArtistName());
                                  displayIntent.putExtra("song_name",lyricsItem.getLyricsName());
                                  displayIntent.putExtra("artist_image",lyricsItem.getImgUrl());

                                  displayIntent.putExtra("lyrics_body",lyricsItem.getSongBody());
                                  displayIntent.putExtra("lyrics_url",lyricsItem.getSongUrl());
                                  context.startActivity(displayIntent);
                                  return true;
                              case R.id.mnu_share_audio:
                                  Intent sendIntent = new Intent();
                                  sendIntent.setAction(Intent.ACTION_SEND);
                                  sendIntent.putExtra(Intent.EXTRA_TEXT,"From DailyNova: "+ lyricsItem.getSongUrl());
                                  sendIntent.setType("text/plain");
                                  context.startActivity(sendIntent);
                                  return true;
                              default:
                                  return false;
                          }

                      }
                  });
                  menu.inflate(R.menu.mnu_popup_lyrics);
                  menu.show();
              }
          });
          lyricsHolder.imgDownloadLyrics.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  DownloadTaskUtil taskUtil=new DownloadTaskUtil(context,lyricsHolder.imgDownloadLyrics,lyricsHolder.progressBar,"audio");
                  taskUtil.execute(lyricsItem.getSongUrl());
              }
          });
          lyricsHolder.imgShareLyrics.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent sendIntent = new Intent();
                  sendIntent.setAction(Intent.ACTION_SEND);
                  sendIntent.putExtra(Intent.EXTRA_TEXT,"From DailyNova: "+ lyricsItem.getSongUrl());
                  sendIntent.setType("text/plain");
                  context.startActivity(sendIntent);
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

          lyricsHolder.imgPlayLyrics.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(lyricsItem.getSongUrl()));
                  intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                  context.startActivity(intent);
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
        private ImageView imgMore,imgArtistImage,imgDownloadLyrics,imgShareLyrics,imgPlayLyrics;
        private ProgressBar progressBar;
        public LyricsHolder(@NonNull View itemView) {
            super(itemView);
            imgArtistImage=(ImageView) itemView.findViewById(R.id.img_artist_image_in_row_lyrics);
            imgDownloadLyrics=(ImageView) itemView.findViewById(R.id.img_download_lyrics_audio_in_row_lyrics);
            imgMore=(ImageView)itemView.findViewById(R.id.img_more_in_row_lyrics);
            imgShareLyrics=(ImageView)itemView.findViewById(R.id.img_share_lyrics_audio_in_row_lyrics);
            txtPostedOn=(TextView)itemView.findViewById(R.id.txt_posted_on_in_row_lyrics);
            txtLyricsArtistName=(TextView)itemView.findViewById(R.id.txt_artist_name_in_row_lyrics);
            txtLyricsName=(TextView)itemView.findViewById(R.id.txt_lyrics_name_in_lyrics_row);
            imgPlayLyrics=(ImageView)itemView.findViewById(R.id.img_play_lyrics_audio_in_row_lyrics);
            progressBar=(ProgressBar)itemView.findViewById(R.id.progress_for_downloading_song_in_lyrics_item);
        }
    }

}
