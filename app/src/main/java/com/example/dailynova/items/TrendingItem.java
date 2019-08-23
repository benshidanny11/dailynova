package com.example.dailynova.items;

public class TrendingItem {
    private String newsBody,newsCategory,newsTitle,newsOwner,newsImgDownloadLink;
    private String lyricsName,ArtistName,imgUrl,SongBody,songUrl,postedOn;
    private String lifeStyleTitle,lifestyleContent,lifestyleSource,lifestyleImageUrl,lifestyleCategory;
    private String videoName,videoDuration,videoSource,vdeoImageUrl,videoDownloadUrl;
    private long videoLikes,videoDownloads;
    public TrendingItem(String lyricsName, String artistName, String imgUrl, String songBody, String songUrl, String postedOn) {
        this.lyricsName = lyricsName;
        ArtistName = artistName;
        this.imgUrl = imgUrl;
        SongBody = songBody;
        this.songUrl = songUrl;
        this.postedOn = postedOn;
    }
    public TrendingItem(String newsBody, String newsCategory, String newsTitle, String newsOwner, String postedOn, String newsImgDownloadLink,int comType) {
        this.newsBody = newsBody;
        this.newsCategory = newsCategory;
        this.newsTitle = newsTitle;
        this.newsOwner = newsOwner;
        this.postedOn = postedOn;
        this.newsImgDownloadLink = newsImgDownloadLink;
    }
    public TrendingItem(String videoName, String videoDuration, String videoSource, String vdeoImageUrl, String videoDownloadUrl, String postedOn, Long videoLikes, Long videoDownloads) {
        this.videoName = videoName;
        this.videoDuration = videoDuration;
        this.videoSource = videoSource;
        this.vdeoImageUrl = vdeoImageUrl;
        this.videoDownloadUrl = videoDownloadUrl;
        this.postedOn = postedOn;
        this.videoLikes = videoLikes;
        this.videoDownloads = videoDownloads;
    }
    public TrendingItem(String lifeStyleTitle, String lifestyleContent, String lifestyleSource, String postedOn, String lifestyleImageUrl, String lifestyleCategory,char type) {
        this.lifeStyleTitle = lifeStyleTitle;
        this.lifestyleContent = lifestyleContent;
        this.lifestyleSource = lifestyleSource;
        this.postedOn = postedOn;
        this.lifestyleImageUrl = lifestyleImageUrl;
        this.lifestyleCategory = lifestyleCategory;
    }

    public String getNewsBody() {
        return newsBody;
    }

    public String getNewsCategory() {
        return newsCategory;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsOwner() {
        return newsOwner;
    }

    public String getNewsImgDownloadLink() {
        return newsImgDownloadLink;
    }

    public String getLyricsName() {
        return lyricsName;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getSongBody() {
        return SongBody;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public String getPostedOn() {
        return postedOn;
    }

    public String getLifeStyleTitle() {
        return lifeStyleTitle;
    }

    public String getLifestyleContent() {
        return lifestyleContent;
    }

    public String getLifestyleSource() {
        return lifestyleSource;
    }

    public String getLifestyleImageUrl() {
        return lifestyleImageUrl;
    }

    public String getLifestyleCategory() {
        return lifestyleCategory;
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

    public long getVideoLikes() {
        return videoLikes;
    }

    public long getVideoDownloads() {
        return videoDownloads;
    }
}
