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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dailynova.DisplayBusinesActivity;
import com.example.dailynova.R;
import com.example.dailynova.items.BusinessItem;

import java.util.ArrayList;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.BusinessHolder> {
   Context context;
   ArrayList<BusinessItem> businessItems;

    public BusinessAdapter(Context context, ArrayList<BusinessItem> businessItems) {
        this.context = context;
        this.businessItems = businessItems;
    }

    @NonNull
    @Override
    public BusinessHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.busines_item_row, viewGroup, false);
        return new BusinessHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessHolder businessHolder, int i) {
      final BusinessItem businessItem=businessItems.get(i);
        businessHolder.txtBusinessTitle.setText(businessItem.getBusinessTitle());
        businessHolder.txtBusinessContent.setText(businessItem.getBusinessContent());
        businessHolder.txtBusinessSource.setText(businessItem.getBusinessSource());
        businessHolder.txtBusinessUploadDate.setText(businessItem.getBusinessUploadDate());
        Glide.with(context).load(businessItem.getBusinessImageUrl()).into(businessHolder.imgBusinessImage);
        businessHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DisplayBusinesActivity.class);
                intent.putExtra("businessImageUrl",businessItem.getBusinessImageUrl());
                intent.putExtra("businessTitle",businessItem.getBusinessTitle());
                intent.putExtra("businessContent",businessItem.getBusinessContent());
                intent.putExtra("businessSource",businessItem.getBusinessSource());
                intent.putExtra("businessCategory",businessItem.getBusinessCategory());
                context.startActivity(intent);
            }
        });
        businessHolder.imgBusinessMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "In TODO more", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return businessItems.size();
    }

    public void updateAdapter(BusinessItem businessItem) {
        businessItems.add(businessItem);
        notifyDataSetChanged();
    }

    class BusinessHolder extends RecyclerView.ViewHolder{
        TextView txtBusinessTitle,txtBusinessContent,txtBusinessSource,txtBusinessUploadDate;
        ImageView imgBusinessImage,imgBusinessMore;
        BusinessHolder(@NonNull View itemView) {
            super(itemView);
            txtBusinessTitle=(TextView)itemView.findViewById(R.id.txt_business_title);
            txtBusinessContent=(TextView)itemView.findViewById(R.id.txt_business_content);
            txtBusinessSource=(TextView)itemView.findViewById(R.id.txt_business_source);
            txtBusinessUploadDate=(TextView)itemView.findViewById(R.id.txt_business_upload_date);
            imgBusinessImage=(ImageView)itemView.findViewById(R.id.img_business_image);
            imgBusinessMore=(ImageView)itemView.findViewById(R.id.img_business_more);
        }
    }
}
