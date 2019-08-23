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
import com.example.dailynova.DisplayNewsActivity;
import com.example.dailynova.R;
import com.example.dailynova.items.NewsItem;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
 Context context;
 ArrayList<NewsItem> newsItems;

    public NewsAdapter(Context context, ArrayList<NewsItem> newsItems) {
        this.context = context;
        this.newsItems = newsItems;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return  new NewsHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_updates_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, int i) {
     final NewsItem item=newsItems.get(i);
     newsHolder.txtNewsTitle.setText(item.getNewsTitle());
     newsHolder.txtNewsContent.setText(item.getNewsBody());
     newsHolder.txtNewsSource.setText(item.getNewsOwner());
     newsHolder.txtNewsUploadDate.setText(item.getNewsUploadDate());
        Glide.with(context).load(item.getNewsImgDownloadLink()).into(newsHolder.imgNewsImage);

        newsHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newsIntent=new Intent(context, DisplayNewsActivity.class);
                newsIntent.putExtra("news_owner",item.getNewsOwner());
                newsIntent.putExtra("news_title",item.getNewsTitle());
                newsIntent.putExtra("news_body",item.getNewsBody());
                newsIntent.putExtra("news_image",item.getNewsImgDownloadLink());
                newsIntent.putExtra("news_upload_date",item.getNewsUploadDate());
                context.startActivity(newsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public void updateAdapter(NewsItem item) {
        newsItems.add(item);
        notifyDataSetChanged();
    }


    class NewsHolder extends RecyclerView.ViewHolder{
          TextView txtNewsTitle,txtNewsContent,txtNewsSource,txtNewsUploadDate;
          ImageView imgNewsImage;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            txtNewsTitle=(TextView)itemView.findViewById(R.id.txt_update_title);
            txtNewsContent=(TextView)itemView.findViewById(R.id.txt_update_content);
            txtNewsSource=(TextView)itemView.findViewById(R.id.txt_update_source);
            txtNewsUploadDate=(TextView)itemView.findViewById(R.id.txt_update_upload_date);
            imgNewsImage=(ImageView)itemView.findViewById(R.id.img_update_image);
        }
    }
}
