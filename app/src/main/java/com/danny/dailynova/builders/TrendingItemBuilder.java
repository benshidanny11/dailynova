package com.danny.dailynova.builders;


import com.danny.dailynova.items.TrendingItem;

public class TrendingItemBuilder {

    private String newsBody,newsCategory,newsTitle,newsOwner,newsImgDownloadLink;
    private String lyricsName,artistName,imgUrl,songBody,songUrl,postedOn;
    private String lifeStyleTitle,lifestyleContent,lifestyleSource,lifestyleImageUrl,lifestyleCategory;
    private String videoName,videoDuration,videoSource,vdeoImageUrl,videoDownloadUrl;
    private long videoLikes,videoDownloads;

    public TrendingItemBuilder setNewsBody(String newsBody) {
        this.newsBody = newsBody;
        return this;
    }

    public TrendingItemBuilder setNewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
        return this;
    }

    public TrendingItemBuilder setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
        return this;
    }

    public TrendingItemBuilder setNewsOwner(String newsOwner) {
        this.newsOwner = newsOwner;
        return this;
    }

    public TrendingItemBuilder setNewsImgDownloadLink(String newsImgDownloadLink) {
        this.newsImgDownloadLink = newsImgDownloadLink;
        return this;
    }

    public TrendingItemBuilder setLyricsName(String lyricsName) {
        this.lyricsName = lyricsName;
        return this;
    }

    public TrendingItemBuilder setArtistName(String artistName) {
        artistName = artistName;
        return this;
    }

    public TrendingItemBuilder setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;

    }

    public TrendingItemBuilder setSongBody(String songBody) {
        songBody = songBody;
        return this;
    }

    public TrendingItemBuilder setSongUrl(String songUrl) {
        this.songUrl = songUrl;
        return this;
    }

    public TrendingItemBuilder setPostedOn(String postedOn) {
        this.postedOn = postedOn;
        return this;
    }

    public TrendingItemBuilder setLifeStyleTitle(String lifeStyleTitle) {
        this.lifeStyleTitle = lifeStyleTitle;
        return this;
    }

    public TrendingItemBuilder setLifestyleContent(String lifestyleContent) {
        this.lifestyleContent = lifestyleContent;
        return this;
    }

    public TrendingItemBuilder setLifestyleSource(String lifestyleSource) {
        this.lifestyleSource = lifestyleSource;
        return this;
    }

    public TrendingItemBuilder setLifestyleImageUrl(String lifestyleImageUrl) {
        this.lifestyleImageUrl = lifestyleImageUrl;
        return this;
    }

    public TrendingItemBuilder setLifestyleCategory(String lifestyleCategory) {
        this.lifestyleCategory = lifestyleCategory;
        return this;
    }

    public TrendingItemBuilder setVideoName(String videoName) {
        this.videoName = videoName;
        return this;
    }

    public TrendingItemBuilder setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
        return this;
    }

    public TrendingItemBuilder setVideoSource(String videoSource) {
        this.videoSource = videoSource;
        return this;
    }

    public TrendingItemBuilder setVdeoImageUrl(String vdeoImageUrl) {
        this.vdeoImageUrl = vdeoImageUrl;
        return this;
    }

    public TrendingItemBuilder setVideoDownloadUrl(String videoDownloadUrl) {
        this.videoDownloadUrl = videoDownloadUrl;
        return this;
    }

    public TrendingItemBuilder setVideoLikes(long videoLikes) {
        this.videoLikes = videoLikes;
        return this;
    }

    public TrendingItemBuilder setVideoDownloads(long videoDownloads) {
        this.videoDownloads = videoDownloads;
        return this;
    }

    public TrendingItem buildVideoItem(){
        return new TrendingItem(videoName,videoDuration,videoSource, vdeoImageUrl, videoDownloadUrl, postedOn, videoLikes,videoDownloads);
    }
    public TrendingItem buildNewsItem(){
        return new TrendingItem(newsBody, newsCategory,newsTitle, newsOwner, postedOn,newsImgDownloadLink,0);
    }
    public TrendingItem buildLyricsItem(){
     return new  TrendingItem(lyricsName,artistName,imgUrl,songBody,songUrl, postedOn);
    }
    public TrendingItem buildLifestyleItem(){
       return new TrendingItem(lifeStyleTitle, lifestyleContent,lifestyleSource,postedOn,lifestyleImageUrl,lifestyleCategory,'a');
    }
}
