package com.example.dailynova.adapters;

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
import com.example.dailynova.DisplayLifeStyleActivity;
import com.example.dailynova.R;
import com.example.dailynova.items.LifeStyleItem;

import java.util.ArrayList;

public class LifestyleAdapter extends RecyclerView.Adapter<LifestyleAdapter.LifestyleHolder> {
     Context context;
     ArrayList<LifeStyleItem> lifeStyleItems;

    public LifestyleAdapter(Context context, ArrayList<LifeStyleItem> lifeStyleItems) {
        this.context = context;
        this.lifeStyleItems = lifeStyleItems;
    }

    @NonNull
    @Override
    public LifestyleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return  new LifestyleHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_life_style, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LifestyleHolder lifestyleHolder, int i) {
      final LifeStyleItem item=lifeStyleItems.get(i);
      lifestyleHolder.txtLifeStyleTitle.setText(item.getLifeStyleTitle());
      lifestyleHolder.txtLifeStyleContent.setText(item.getLifestyleContent());
      lifestyleHolder.txtLifeStyleSource.setText(item.getLifestyleSource());
      lifestyleHolder.txtLifeStyleUploadDate.setText(item.getLifestyleUploadDate());
        Glide.with(context).load(item.getLifestyleImageUrl()).into(lifestyleHolder.imgLifeStyleImage);

        lifestyleHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lifesyleIntent=new Intent(context, DisplayLifeStyleActivity.class);
                lifesyleIntent.putExtra("lifestyle_title",item.getLifeStyleTitle());
                lifesyleIntent.putExtra("lifestyle_source",item.getLifestyleSource());
                lifesyleIntent.putExtra("lifestyle_body",item.getLifestyleContent());
                lifesyleIntent.putExtra("lifestyle_category",item.getLifestyleCategory());
                lifesyleIntent.putExtra("lifestyle_image",item.getLifestyleImageUrl());
                context.startActivity(lifesyleIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lifeStyleItems.size();
    }

    public void updateAdapter(LifeStyleItem item) {
        lifeStyleItems.add(item);
        notifyDataSetChanged();
    }

    class LifestyleHolder extends RecyclerView.ViewHolder{
        TextView txtLifeStyleTitle,txtLifeStyleContent,txtLifeStyleUploadDate,txtLifeStyleSource;
        ImageView imgLifeStyleImage;
        public LifestyleHolder(@NonNull View itemView) {
            super(itemView);
            txtLifeStyleTitle=(TextView)itemView.findViewById(R.id.txt_lifestyle_title);
            txtLifeStyleContent=(TextView)itemView.findViewById(R.id.txt_lifestyle_content);
            txtLifeStyleSource=(TextView)itemView.findViewById(R.id.txt_lifestyle_source);
            txtLifeStyleUploadDate=(TextView)itemView.findViewById(R.id.txt_lifestyle_upload_date);
            imgLifeStyleImage=(ImageView)itemView.findViewById(R.id.img_lifestyle_image);

        }
    }
}
