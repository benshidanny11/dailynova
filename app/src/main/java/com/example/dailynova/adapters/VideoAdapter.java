package com.example.dailynova.adapters;

import android.content.Context;
import android.content.Intent;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dailynova.PlayVideoActivity;
import com.example.dailynova.R;
import com.example.dailynova.items.VideoItem;
import com.example.dailynova.utils.DownloadTaskUtil;

import java.util.ArrayList;



public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private Context context;
    private ArrayList<VideoItem> videoItems;

    public VideoAdapter(Context context, ArrayList<VideoItem> videoItems) {
        this.context = context;
        this.videoItems = videoItems;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return  new VideoHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_video_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoHolder videoHolder, int i) {
        final VideoItem videoItem=videoItems.get(i);
        videoHolder.fullscreenVideo.setPadding(0,0,0,0);
        videoHolder.txtVideoName.setText(videoItem.getVideoName());
        videoHolder.txtVideoSource.setText(videoItem.getVideoSource());
        videoHolder.txtPostedOn.setText(videoItem.getPostedOn());
        videoHolder.txtVideoDuration.setText(videoItem.getVideoDuration());
        videoHolder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Not yet added!", Toast.LENGTH_SHORT).show();
            }
        });
        videoHolder.imgShareVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"From DailyNova: "+ videoItem.getVideoDownloadUrl());
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);


            }
        });
        videoHolder.imgDownloadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadTaskUtil taskUtil=new DownloadTaskUtil(context,videoHolder.imgDownloadVideo,videoHolder.progressBar,"video");
                taskUtil.execute(videoItem.getVideoDownloadUrl());
                }
        });

        Glide.with(context).load(videoItem.getVdeoImageUrl()).centerCrop().placeholder(R.drawable.loading).into(videoHolder.fullscreenVideo);

        videoHolder.fullscreenVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent playIntent=new Intent(context, PlayVideoActivity.class);
                playIntent.putExtra("videoUrl",videoItem.getVideoDownloadUrl());
                playIntent.putExtra("videoName",videoItem.getVideoName());
                playIntent.putExtra("videoDuration",videoItem.getVideoDuration());
                playIntent.putExtra("videoLikes",videoItem.getVideoLikes());
                playIntent.putExtra("videoDownloads",videoItem.getVideoDownloads());
                playIntent.putExtra("videoSource",videoItem.getVideoSource());
                context.startActivity(playIntent);
            }
        });

        videoHolder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu=new PopupMenu(context,v);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.mnu_play_video:
                                Intent playIntent=new Intent(context, PlayVideoActivity.class);
                                playIntent.putExtra("videoUrl",videoItem.getVideoDownloadUrl());
                                playIntent.putExtra("videoName",videoItem.getVideoName());
                                playIntent.putExtra("videoDuration",videoItem.getVideoDuration());
                                playIntent.putExtra("videoLikes",videoItem.getVideoLikes());
                                playIntent.putExtra("videoDownloads",videoItem.getVideoDownloads());
                                playIntent.putExtra("videoSource",videoItem.getVideoSource());
                                context.startActivity(playIntent);
                                return true;
                            case R.id.mnu_view_later:
                                Toast.makeText(context, "Added to view later", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.mnu_share_video:
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT,"From DailyNova: "+ videoItem.getVideoDownloadUrl());
                                sendIntent.setType("text/plain");
                                context.startActivity(sendIntent);
                                return true;
                            default:
                                return false;
                        }

                    }
                });
                menu.inflate(R.menu.popup_menu_video);
                menu.show();
            }
        });


    }



    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        TextView txtVideoName, txtVideoDuration, txtPostedOn, txtVideoSource;
        ImageView imgMore, imgShareVideo, imgDownloadVideo,imgLikeVideo;
        ImageView fullscreenVideo;
        ProgressBar progressBar;

        public VideoHolder(View itemView) {
            super(itemView);
            txtVideoName = (TextView) itemView.findViewById(R.id.txt_video_name_in_lyrics_row);
            txtVideoDuration = (TextView) itemView.findViewById(R.id.txt_video_duration);
            txtPostedOn = (TextView) itemView.findViewById(R.id.txt_posted_on_in_row_video);
            txtVideoSource = (TextView) itemView.findViewById(R.id.txt_video_source_in_row_video);
            fullscreenVideo = (ImageView) itemView.findViewById(R.id.fullscreen_in_row_video);
            imgMore=(ImageView)itemView.findViewById(R.id.img_more_in_row_video);
            imgShareVideo=(ImageView)itemView.findViewById(R.id.img_share_video_in_row_video);
            imgDownloadVideo=(ImageView)itemView.findViewById(R.id.img_download_video_in_row_video);
            progressBar=(ProgressBar)itemView.findViewById(R.id.progress_for_downloading_video_in_video_item);

        }

    }
    public void updateAdapter(VideoItem videoItem){
        videoItems.add(videoItem);
        notifyDataSetChanged();
    }
}
