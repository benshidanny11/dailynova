package com.example.dailynova.items;

public class VideoItem {
    String videoName,videoDuration,videoSource,vdeoImageUrl,videoDownloadUrl,postedOn;
    Long videoLikes,videoDownloads;

    public VideoItem(String videoName, String videoDuration, String videoSource, String vdeoImageUrl, String videoDownloadUrl, String postedOn, Long videoLikes, Long videoDownloads) {
        this.videoName = videoName;
        this.videoDuration = videoDuration;
        this.videoSource = videoSource;
        this.vdeoImageUrl = vdeoImageUrl;
        this.videoDownloadUrl = videoDownloadUrl;
        this.postedOn = postedOn;
        this.videoLikes = videoLikes;
        this.videoDownloads = videoDownloads;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public String getVideoSource() {
        return videoSource;
    }

    public String getVdeoImageUrl() {
        return vdeoImageUrl;
    }

    public String getVideoDownloadUrl() {
        return videoDownloadUrl;
    }

    public String getPostedOn() {
        return postedOn;
    }

    public Long getVideoLikes() {
        return videoLikes;
    }

    public Long getVideoDownloads() {
        return videoDownloads;
    }
}
