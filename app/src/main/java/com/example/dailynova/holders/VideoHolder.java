package com.example.dailynova.holders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public abstract class VideoHolder extends RecyclerView.ViewHolder {

    public VideoHolder(View itemView) {
        super(itemView);
    }

    public abstract View getVideoLayout();

    public abstract void playVideo();

    public abstract void stopVideo();
}
