package com.danny.dailynova.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.danny.dailynova.DisplayNewsActivity;
import com.danny.dailynova.R;
import com.danny.dailynova.items.TrendingItem;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  Context context;
  ArrayList<TrendingItem> homeItems;

    public HomeAdapter(Context context, ArrayList<TrendingItem> homeItems) {
        this.context = context;
        this.homeItems = homeItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;

            view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_updates_item, viewGroup, false);
            viewHolder=new HomeUpdatesHolder(view);
            return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
             final TrendingItem item=homeItems.get(i);
             HomeUpdatesHolder newsHolder=(HomeUpdatesHolder)viewHolder;
             newsHolder.txtNewsTitle.setText(item.getNewsTitle());
             newsHolder.txtNewsContent.setText(item.getNewsBody());
             newsHolder.txtNewsSource.setText(item.getNewsOwner());
             newsHolder.txtNewsUploadDate.setText(item.getPostedOn());
             Glide.with(context).load(item.getNewsImgDownloadLink()).into(newsHolder.imgNewsImage);

             newsHolder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent newsIntent=new Intent(context, DisplayNewsActivity.class);
                     newsIntent.putExtra("news_owner",item.getNewsOwner());
                     newsIntent.putExtra("news_title",item.getNewsTitle());
                     newsIntent.putExtra("news_body",item.getNewsBody());
                     newsIntent.putExtra("news_image",item.getNewsImgDownloadLink());
                     newsIntent.putExtra("news_upload_date",item.getPostedOn());
                     context.startActivity(newsIntent);
                 }
             });




    }

    @Override
    public int getItemCount() {
        return homeItems.size();
    }


    class HomeVideosHolder extends RecyclerView.ViewHolder {
        TextView txtVideoName, txtVideoDuration, txtPostedOn, txtVideoSource;
        ImageView imgMore, imgShareVideo, imgDownloadVideo;
        ImageView fullscreenVideo;
        public HomeVideosHolder(@NonNull View itemView) {
            super(itemView);
            txtVideoName = (TextView) itemView.findViewById(R.id.txt_video_name_in_lyrics_row);
            txtVideoDuration = (TextView) itemView.findViewById(R.id.txt_video_duration);
            txtPostedOn = (TextView) itemView.findViewById(R.id.txt_posted_on_in_row_video);
            txtVideoSource = (TextView) itemView.findViewById(R.id.txt_video_source_in_row_video);
            fullscreenVideo = (ImageView) itemView.findViewById(R.id.fullscreen_in_row_video);
            imgMore=(ImageView)itemView.findViewById(R.id.img_more_in_row_video);
            imgShareVideo=(ImageView)itemView.findViewById(R.id.img_share_video_in_row_video);
            imgDownloadVideo=(ImageView)itemView.findViewById(R.id.img_download_video_in_row_video);
        }
    }
    class HomeLyricsHolder extends RecyclerView.ViewHolder{
        private TextView txtLyricsName,txtLyricsArtistName,txtPostedOn;
        private ImageView imgMore,imgArtistImage,imgDownloadLyrics,imgShareLyrics;
        public HomeLyricsHolder(@NonNull View itemView) {
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
    class HomeLifestyleHolder extends RecyclerView.ViewHolder{
        TextView txtLifeStyleTitle,txtLifeStyleContent,txtLifeStyleUploadDate,txtLifeStyleSource;
        ImageView imgLifeStyleImage;
        public HomeLifestyleHolder(@NonNull View itemView) {
            super(itemView);
            txtLifeStyleTitle=(TextView)itemView.findViewById(R.id.txt_lifestyle_title);
            txtLifeStyleContent=(TextView)itemView.findViewById(R.id.txt_lifestyle_content);
            txtLifeStyleSource=(TextView)itemView.findViewById(R.id.txt_lifestyle_source);
            txtLifeStyleUploadDate=(TextView)itemView.findViewById(R.id.txt_lifestyle_upload_date);
            imgLifeStyleImage=(ImageView)itemView.findViewById(R.id.img_lifestyle_image);
        }
    }
    class HomeUpdatesHolder extends RecyclerView.ViewHolder{
        TextView txtNewsTitle,txtNewsContent,txtNewsSource,txtNewsUploadDate;
        ImageView imgNewsImage;
        public HomeUpdatesHolder(@NonNull View itemView) {
            super(itemView);
            txtNewsTitle=(TextView)itemView.findViewById(R.id.txt_update_title);
            txtNewsContent=(TextView)itemView.findViewById(R.id.txt_update_content);
            txtNewsSource=(TextView)itemView.findViewById(R.id.txt_update_source);
            txtNewsUploadDate=(TextView)itemView.findViewById(R.id.txt_update_upload_date);
            imgNewsImage=(ImageView)itemView.findViewById(R.id.img_update_image);
        }
    }

    public void upateAdapter(TrendingItem trendingItem){
        homeItems.add(trendingItem);
        notifyDataSetChanged();
    }

}
