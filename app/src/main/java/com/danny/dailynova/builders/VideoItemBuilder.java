package com.danny.dailynova.builders;


import com.danny.dailynova.items.VideoItem;

public class VideoItemBuilder {
    String videoName,videoDuration,videoSource,vdeoImageUrl,videoDownloadUrl,postedOn;
    Long videoLikes,videoDownloads;

    public VideoItemBuilder setVideoName(String videoName) {
        this.videoName = videoName;
        return this;
    }

    public VideoItemBuilder setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
        return this;
    }

    public VideoItemBuilder setVideoSource(String videoSource) {
        this.videoSource = videoSource;
        return this;
    }

    public VideoItemBuilder setVdeoImageUrl(String vdeoImageUrl) {
        this.vdeoImageUrl = vdeoImageUrl;
        return this;
    }

    public VideoItemBuilder setVideoDownloadUrl(String videoDownloadUrl) {
        this.videoDownloadUrl = videoDownloadUrl;
        return this;
    }

    public VideoItemBuilder setPostedOn(String postedOn) {
        this.postedOn = postedOn;
        return this;
    }

    public VideoItemBuilder setVideoLikes(Long videoLikes) {
        this.videoLikes = videoLikes;
        return this;
    }

    public VideoItemBuilder setVideoDownloads(Long videoDownloads) {
        this.videoDownloads = videoDownloads;
        return this;
    }
    public VideoItem build(){
        return new VideoItem(videoName,videoDuration,videoSource,vdeoImageUrl,videoDownloadUrl,postedOn,videoLikes,videoDownloads);
    }
}
