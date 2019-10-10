package com.example.dailynova.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dailynova.R;
import com.example.dailynova.items.VideoItem;
import com.example.dailynova.utils.PlayActivityUtil;
import com.example.dailynova.utils.ViewTypeConstants;

import java.util.ArrayList;

public class VideoPlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ViewGroup.LayoutParams params;
    private Context context;
    private ArrayList<VideoItem> videoItems;

    public VideoPlayerAdapter(Context context, ArrayList<VideoItem> videoItems) {
        this.context = context;
        this.videoItems = videoItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=null;
        RecyclerView.ViewHolder viewHolder=null;
        if (i==ViewTypeConstants.TOP_VIEW_TYPE){
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_player_top_item,viewGroup,false);
            viewHolder=new TopItemVIewHolder(view);
        }
        else{
            view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.play_video_item,viewGroup,false);
            viewHolder=new PlayerVideoHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
     if (viewHolder.getItemViewType()==ViewTypeConstants.TOP_VIEW_TYPE){
         VideoItem videoItem=videoItems.get(i);
         TopItemVIewHolder videoHolder=(TopItemVIewHolder) viewHolder;
         videoHolder.videoNameInTop.setText(videoItem.getVideoName());
         videoHolder.videoSourceInTop.setText(videoItem.getVideoSource());
         videoHolder.downLoadVideoInTop.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(context, "Not yet added", Toast.LENGTH_SHORT).show();
             }
         });
         videoHolder.shareVideoInTop.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(context, "Not yet added", Toast.LENGTH_SHORT).show();
             }
         });
     }
     else{
      final VideoItem videoItem=videoItems.get(i);
      PlayerVideoHolder videoHolder=(PlayerVideoHolder)viewHolder;
      videoHolder.txtVideoName.setText(videoItem.getVideoName());
      videoHolder.txtVideoSource.setText(videoItem.getVideoSource());
      videoHolder.txtVideoDuration.setText(videoItem.getVideoDuration());
      videoHolder.txtUploadedOn.setText(videoItem.getPostedOn());
         RequestOptions options = new RequestOptions();
         params=videoHolder.imgVideoThumb.getLayoutParams();
         WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
         Point ptSize=new Point();
         Display display = wm.getDefaultDisplay();
         DisplayMetrics dm=context.getResources().getDisplayMetrics();

         display.getSize(ptSize);
         params.height=dm.widthPixels;
         videoHolder.imgVideoThumb.setLayoutParams(params);
         options.fitCenter();
         Glide.with(context).load(videoItem.getVdeoImageUrl()).into(((PlayerVideoHolder) viewHolder).imgVideoThumb);
      videoHolder.imgMore.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              PopupMenu menu=new PopupMenu(context,v);
              menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                  @Override
                  public boolean onMenuItemClick(MenuItem item) {

                      switch (item.getItemId()){
                          case R.id.mnu_play_video:
                              Intent playIntent=new Intent(context, PlayActivityUtil.class);
                              ((Activity)context).finish();
                              playIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
                              playIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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
         viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent playIntent=new Intent(context, PlayActivityUtil.class);
                 ((Activity)context).finish();
                 playIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
                 playIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                 playIntent.putExtra("videoUrl",videoItem.getVideoDownloadUrl());
                 playIntent.putExtra("videoName",videoItem.getVideoName());
                 playIntent.putExtra("videoDuration",videoItem.getVideoDuration());
                 playIntent.putExtra("videoLikes",videoItem.getVideoLikes());
                 playIntent.putExtra("videoDownloads",videoItem.getVideoDownloads());
                 playIntent.putExtra("videoSource",videoItem.getVideoSource());
                 context.startActivity(playIntent);

             }
         });

     }


    }


    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return ViewTypeConstants.TOP_VIEW_TYPE;
        }else {
            return ViewTypeConstants.VIDEO_TYPE_IN_PLAY;
        }
    }

    class TopItemVIewHolder extends RecyclerView.ViewHolder{
        TextView videoNameInTop,videoSourceInTop;
        ImageView downLoadVideoInTop,shareVideoInTop;
        public TopItemVIewHolder(@NonNull View itemView) {
            super(itemView);
            videoNameInTop=(TextView)itemView.findViewById(R.id.txt_video_name_in_player_top_item);
            videoSourceInTop=(TextView)itemView.findViewById(R.id.txt_video_source_in_player_top_item);
            downLoadVideoInTop=(ImageView) itemView.findViewById(R.id.img_download_video_in_player_top_item);
            shareVideoInTop=(ImageView)itemView.findViewById(R.id.img_share_video_in_player_top_item);
        }
    }

    class PlayerVideoHolder extends RecyclerView.ViewHolder{
        TextView txtVideoName,txtVideoSource,txtVideoDuration,txtUploadedOn;
        ImageView imgVideoThumb,imgMore;
        public PlayerVideoHolder(@NonNull View itemView) {
            super(itemView);
            txtVideoName=(TextView)itemView.findViewById(R.id.txt_video_name_in_video_play);
            txtVideoDuration=(TextView)itemView.findViewById(R.id.txt_video_duration_in_play);
            txtUploadedOn=(TextView)itemView.findViewById(R.id.txt_video_upload_date_in_play);
            txtVideoSource=(TextView)itemView.findViewById(R.id.txt_video_source_in_play);
            imgVideoThumb=(ImageView)itemView.findViewById(R.id.img_video_thumb_in_play_row);
            imgMore=(ImageView)itemView.findViewById(R.id.img_more_in_play);
        }

    }

    public void updateAdapter(VideoItem videoItem){
        videoItems.add(videoItem);
        notifyDataSetChanged();
    }
}
